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
import com.anthonykim.smartfactory.imdg.table.HEAT;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class HEATSimulator implements Runnable {
	private int writeMode;
	private String query;

	public final static int WRITE_DB = 0x01;
	public final static int WRITE_IMDG = 0x02;
	
public HEATSimulator(int writeMode) {
		this.writeMode = writeMode;
	}

	@Override
	public void run() {
		if (writeMode == WRITE_DB)
			pushDatabase();
		else if (writeMode == WRITE_IMDG)
			pushIMDG();
	}
	
	private void pushDatabase() {
		ConnectionManager connMgr = new MySQLConnectionManager();
		Connection conn = connMgr.getConnection();
		PreparedStatement pstmt = null;

		query = "insert into SensorDB.DN_1_09(id, volt, ampere, eOutput, amount, temperature, flow, rpm, workTime) values(?,?,?,?,?,?,?,?,?);";
		
		try {
			int id = 0;
			int counter = 0;
			Random rand = new Random();
			
			pstmt = conn.prepareStatement(query);
			while (counter++ < SmartFactoryIMDG.MAX_RECORD) {
				double volt = (rand.nextDouble() * 20.0) + 295.0;
				double ampere = (rand.nextDouble() * 10.0) + 100.0;
				double eOutput = (rand.nextDouble() * 2.0) + 30.0;
				int amount = rand.nextInt(1000) + 500;
				double temperature = (rand.nextDouble() * 10.0) + 25.0;
				double flow = (rand.nextDouble() * 5.0) + 25.0;;
				int rpm = rand.nextInt(990) + 20;
				int workTime = rand.nextInt(4) + 1;
				
				pstmt.setInt(1, id++);
				pstmt.setDouble(2, volt);
				pstmt.setDouble(3, ampere);
				pstmt.setDouble(4, eOutput);
				pstmt.setInt(5, amount);
				pstmt.setDouble(6, temperature);
				pstmt.setDouble(7, flow);
				pstmt.setInt(8, rpm);
				pstmt.setInt(9, workTime);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabase(connMgr, conn, pstmt, null);
		}
	}
	
	private HEAT getNextHEAT(MultiMap<Integer, Object> database) {
		Collection<Object> column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_09);
		Iterator<Object> iterator = column.iterator();
		if (iterator.hasNext())
			return ((HEAT) iterator.next());
		
		return null;
	}
	
	private void pushIMDG() {
		ClientConfig clientConfig = new ClientConfig();
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		MultiMap<Integer, Object> database = client.getMultiMap("SensorDB");
		
		HEAT nextHEAT = getNextHEAT(database);
		Random rand = new Random();

		int id = 0;
		if (nextHEAT != null)
			id = nextHEAT.getId() + 1;

		while (true) {
			double volt = (rand.nextDouble() * 20.0) + 295.0;
			double ampere = (rand.nextDouble() * 10.0) + 100.0;
			double eOutput = (rand.nextDouble() * 2.0) + 30.0;
			int amount = rand.nextInt(1000) + 500;
			double temperature = (rand.nextDouble() * 10.0) + 25.0;
			double flow = (rand.nextDouble() * 5.0) + 25.0;;
			int rpm = rand.nextInt(990) + 20;
			int workTime = rand.nextInt(4) + 1;
			
			database.put(SmartFactoryIMDG.DN_1_09, new HEAT(id++, volt, ampere, eOutput, amount, temperature, flow, rpm, workTime, HEAT.NOT_SYNCHED));
			sleep(500);
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
