package com.anthonykim.smartfactory.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;
import com.smartfactory.imdg.db.ConnectionManager;
import com.smartfactory.imdg.db.MySQLConnectionManager;
import com.smartfactory.imdg.hazelcast.SmartFactoryIMDG;
import com.smartfactory.imdg.table.HEAT;
import com.smartfactory.imdg.table.INSPECTION;
import com.smartfactory.imdg.table.POLISH;

public class POLISHSimulator implements Runnable {
	private String query;
	private int writeMode;
	
	public final static int WRITE_DB = 0x01;
	public final static int WRITE_IMDG = 0x02;
	
	public POLISHSimulator(int writeMode) {
		this.writeMode = writeMode;
	}

	@Override
	public void run() {
		if (writeMode == WRITE_DB)
			pushDatabase();
		else if (writeMode == WRITE_IMDG)
			pushIMDG();
	}	
	
	private POLISH getNextPOLISH(MultiMap<Integer, Object> database) {
		Collection<Object> column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_15);
		
		Iterator<Object> iterator = column.iterator();
		if (iterator.hasNext())
			return ((POLISH) iterator.next());
		
		return null;
	}
	
	private void pushIMDG() {
		ClientConfig clientConfig = new ClientConfig();
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		MultiMap<Integer, Object> database = client.getMultiMap("SensorDB");
		
		POLISH nextPOLISH = getNextPOLISH(database);
		Random rand = new Random();

		int id = 0;
		if (nextPOLISH != null)
			id = nextPOLISH.getId() + 1;

		while (true) {
			double wholeLength = rand.nextDouble() + 12.0;
			double shaking = rand.nextDouble();
			
			database.put(SmartFactoryIMDG.DN_1_15, new POLISH(id++, wholeLength, shaking, POLISH.NOT_SYNCHED));
			sleep(500);
		}
	}
	
	private void pushDatabase() {
		ConnectionManager connMgr = new MySQLConnectionManager();
		Connection conn = connMgr.getConnection();
		PreparedStatement pstmt = null;

		query = "insert into SensorDB.DN_1_15(id, wholeLength, shaking) values(?,?,?);";
		
		try {
			int id = 0;
			int counter = 0;
			Random rand = new Random();
			pstmt = conn.prepareStatement(query);
			
			while (counter++ < SmartFactoryIMDG.MAX_RECORD) {
				double wholeLength = rand.nextDouble() + 12.0;
				double shaking = rand.nextDouble();
				
				pstmt.setInt(1, id++);
				pstmt.setDouble(1, wholeLength);
				pstmt.setDouble(2, shaking);
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
