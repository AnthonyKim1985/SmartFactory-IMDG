package com.anthonykim.smartfactory.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.anthonykim.smartfactory.imdg.db.ConnectionManager;
import com.anthonykim.smartfactory.imdg.db.MySQLConnectionManager;
import com.anthonykim.smartfactory.imdg.hazelcast.SmartFactoryIMDG;
import com.anthonykim.smartfactory.imdg.table.RACK;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class RACKSimulator implements Runnable {
	private String query;
	private int machineNo;
	private int writeMode;
	
	public final static int WRITE_DB = 0x01;
	public final static int WRITE_IMDG = 0x02;
	

	public RACKSimulator(int machineNo, int writeMode) {
		this.machineNo = machineNo;
		this.writeMode = writeMode;
	}

	@Override
	public void run() {
		if (writeMode == WRITE_DB)
			pushDatabase();
		else if (writeMode == WRITE_IMDG)
			pushIMDG();
	}	
	
	private RACK getNextRACK(MultiMap<Integer, Object> database) {
		Collection<Object> column = null;
		
		switch (machineNo) {
		case SmartFactoryIMDG.DN_1_11: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_11); break;
		case SmartFactoryIMDG.DN_1_12: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_12); break;
		case SmartFactoryIMDG.DN_1_13: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_13); break;
		}
		
		Iterator<Object> iterator = column.iterator();
		if (iterator.hasNext())
			return ((RACK) iterator.next());
		
		return null;
	}
	
	private void pushIMDG() {
		ClientConfig clientConfig = new ClientConfig();
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		MultiMap<Integer, Object> database = client.getMultiMap("SensorDB");
		
		RACK nextRACK = getNextRACK(database);
		Random rand = new Random();

		int id = 0;
		if (nextRACK != null)
			id = nextRACK.getId() + 1;

		while (true) {
			int dise = rand.nextInt(3) + 1;
			int count = rand.nextInt(59000) + 1000;
			int temperature = rand.nextInt(100) + 200;
			int countSum = rand.nextInt(5000) + 1000;
			
			database.put(machineNo, new RACK(id++, dise, count, temperature, countSum, RACK.NOT_SYNCHED));
			sleep(500);
		}
	}
	
	private void pushDatabase() {
		ConnectionManager connMgr = new MySQLConnectionManager();
		Connection conn = connMgr.getConnection();

		switch (machineNo) {
		case SmartFactoryIMDG.DN_1_11: query = "insert into SensorDB.DN_1_11(id, dise, count, temperature, countSum) values(?,?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_12: query = "insert into SensorDB.DN_1_12(id, dise, count, temperature, countSum) values(?,?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_13: query = "insert into SensorDB.DN_1_13(id, dise, count, temperature, countSum) values(?,?,?,?,?);"; break;
		}
		
		PreparedStatement pstmt = null;
		Random rand = new Random();
		
		try {
			int id = 0;
			int counter = 0;
			
			pstmt = conn.prepareStatement(query);
			while (counter++ < SmartFactoryIMDG.MAX_RECORD) {
				int dise = rand.nextInt(3) + 1;
				int count = rand.nextInt(59000)+1000;
				int temperature = rand.nextInt(100)+200;
				int countSum = rand.nextInt(5000)+1000;
				
				pstmt.setInt(1, id++);
				pstmt.setInt(2, dise);
				pstmt.setInt(3, count);
				pstmt.setInt(4, temperature);
				pstmt.setInt(5, countSum);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabase(connMgr, conn, pstmt, null);
		}
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeDatabase(ConnectionManager connMgr, Connection conn, PreparedStatement pstmt, ResultSet resultSet) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (resultSet != null)
				resultSet.close();
			if (conn != null)
				connMgr.freeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}