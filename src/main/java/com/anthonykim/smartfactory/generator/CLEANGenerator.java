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
import com.smartfactory.imdg.table.CLEAN;
import com.smartfactory.imdg.table.CNC;

public class CLEANGenerator implements Runnable {
	private String query;
	private int writeMode;
	
	public final static int WRITE_DB = 0x01;
	public final static int WRITE_IMDG = 0x02;

	public CLEANGenerator(int writeMode) {
		this.writeMode = writeMode;
	}

	@Override
	public void run() {
		if (writeMode == WRITE_DB)
			pushDatabase();
		else if (writeMode == WRITE_IMDG)
			pushIMDG();
	}
	
	private CLEAN getNextCLEAN(MultiMap<Integer, Object> database) {
		Collection<Object> column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_14);
		Iterator<Object> iterator = column.iterator();
		if (iterator.hasNext())
			return ((CLEAN) iterator.next());
		
		return null;
	}
	
	private void pushIMDG() {
		ClientConfig clientConfig = new ClientConfig();
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		MultiMap<Integer, Object> database = client.getMultiMap("SensorDB");
		
		CLEAN nextCLEAN = getNextCLEAN(database);
		Random rand = new Random();
		
		int id = 0;
		if (nextCLEAN != null)
			id = nextCLEAN.getId() + 1;

		while (true) {
			double output1 = rand.nextDouble() * 300.0 + 10;
			double outAngle = rand.nextDouble() * 301;
			double correctAngle = rand.nextDouble() * 100 + 200;
			int correctPart = rand.nextInt(10000);
			int dataCount = rand.nextInt(100)+1;
			double output2 = rand.nextDouble()*0.2 + 0.001;
			double result1 = rand.nextDouble()*0.5 + 0.001;
			double result2 = rand.nextDouble()*0.5 + 0.001;
			double result3 = rand.nextDouble()*0.5 + 0.001;
			double correction = rand.nextDouble()*3.0 + 0.00001;
			int fairAmount = rand.nextInt(150001);
			int faultAmount = rand.nextInt(51);
			
			database.put(SmartFactoryIMDG.DN_1_14, 
					new CLEAN(id++, output1, outAngle, correctAngle, correctPart, dataCount, 
							output2, result1, result2, result3, correction, fairAmount, faultAmount, CLEAN.NOT_SYNCHED));
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
	
	private void pushDatabase() {
		ConnectionManager connMgr = new MySQLConnectionManager();
		Connection conn = connMgr.getConnection();
		query = "insert into SensorDB.DN_1_14(id, output1, outAngle, correctAngle, correctPart, dataCount, output2, result1, result2, result3, correction, fairAmount, faultAmount) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
		PreparedStatement pstmt = null;
		Random rand = new Random();
		int counter = 0;
		int id = 0;
		try {
			pstmt = conn.prepareStatement(query);
			while (counter++ < SmartFactoryIMDG.MAX_RECORD) {
				double output1 = rand.nextDouble() * 300.0 + 10;
				double outAngle = rand.nextDouble() * 301;
				double correctAngle = rand.nextDouble() * 100 + 200;
				int correctPart = rand.nextInt(10000);
				int dataCount = rand.nextInt(100)+1;
				double output2 = rand.nextDouble()*0.2 + 0.001;
				double result1 = rand.nextDouble()*0.5 + 0.001;
				double result2 = rand.nextDouble()*0.5 + 0.001;
				double result3 = rand.nextDouble()*0.5 + 0.001;
				double correction = rand.nextDouble()*3.0 + 0.00001;
				int fairAmount = rand.nextInt(150001);
				int faultAmount = rand.nextInt(51);
				
				pstmt.setInt(1, id++);
				pstmt.setDouble(2, output1);
				pstmt.setDouble(3, outAngle);
				pstmt.setDouble(4, correctAngle);
				pstmt.setInt(5, correctPart);
				pstmt.setInt(6, dataCount);
				pstmt.setDouble(7, output2);
				pstmt.setDouble(8, result1);
				pstmt.setDouble(9, result2);
				pstmt.setDouble(10, result3);
				pstmt.setDouble(11, correction);
				pstmt.setInt(12, fairAmount);
				pstmt.setInt(13, faultAmount);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabase(connMgr, conn, pstmt, null);
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