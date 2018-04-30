package com.anthonykim.smartfactory.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import com.anthonykim.smartfactory.imdg.db.ConnectionManager;
import com.anthonykim.smartfactory.imdg.db.MySQLConnectionManager;
import com.anthonykim.smartfactory.imdg.hazelcast.SmartFactoryIMDG;
import com.anthonykim.smartfactory.imdg.table.INSPECTION;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class INSPECTIONSimulator implements Runnable {
	private int writeMode;
	private String query;

	public final static int WRITE_DB = 0x01;
	public final static int WRITE_IMDG = 0x02;

	public INSPECTIONSimulator(int writeMode) {
		this.writeMode = writeMode;
	}

	@Override
	public void run() {
		if (writeMode == WRITE_DB)
			pushDatabase();
		else if (writeMode == WRITE_IMDG)
			pushIMDG();
	}
	
	private INSPECTION getNextINSPECTION(MultiMap<Integer, Object> database) {
		Collection<Object> column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_19);
		
		Iterator<Object> iterator = column.iterator();
		if (iterator.hasNext())
			return ((INSPECTION) iterator.next());
		
		return null;
	}
	
	private void pushIMDG() {
		ClientConfig clientConfig = new ClientConfig();
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		MultiMap<Integer, Object> database = client.getMultiMap("SensorDB");
				
		INSPECTION nextINSPECTION = getNextINSPECTION(database);
		Random rand = new Random();
		
		int id = 0;
		if (nextINSPECTION != null)	
			id = nextINSPECTION.getId() + 1;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		while (true) {
			String time = format.format(new Date()).toString(); 
			String type = "L2";
			String result = "OK";
			double exDiameter1 = rand.nextDouble() * 4.0 + 3.0;
			double exDiameter2 = rand.nextDouble() * 8.0 + 7.0;
			double exDiameter3 = rand.nextDouble() * 8.0 + 7.0;
			double whole = rand.nextDouble() * 27.0 + 26.0;
			double height = rand.nextDouble() * 2.5 + 2.0;
			double splitPoint = rand.nextDouble() * 0.05 + 0.0001;
			double roundness1 = rand.nextDouble() * 0.0001;
			double staking = rand.nextDouble() * 0.1 + 0.05;
			double roundness2 = rand.nextDouble() * 0.0001;
			
			database.put(SmartFactoryIMDG.DN_1_19, new INSPECTION(id++, time, type, result,
					exDiameter1, exDiameter2, exDiameter3, whole, height, splitPoint, roundness1, staking, roundness2, INSPECTION.NOT_SYNCHED));
			sleep(500);
		}
	}
	
	private void pushDatabase() {
		ConnectionManager connMgr = new MySQLConnectionManager();
		Connection conn = connMgr.getConnection();
		query = "insert into SensorDB.DN_1_19(id,time,type,result,exDiameter1,exDiameter2,exDiameter3,whole,height,splitPoint,roundness1,staking,roundness2) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
		PreparedStatement pstmt = null;
		Random rand = new Random();
		
		try {
			int id = 0;
			int counter = 0;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			pstmt = conn.prepareStatement(query);

			while (counter++ < SmartFactoryIMDG.MAX_RECORD) {
				String time = format.format(new Date()).toString(); 
				String type = "L2";
				String result = "OK";
				double exDiameter1 = rand.nextDouble()*4.0 +3.0;
				double exDiameter2 = rand.nextDouble()*8.0 +7.0;
				double exDiameter3 = rand.nextDouble()*8.0+7.0;
				double whole = rand.nextDouble()*27.0 + 26.0;
				double height = rand.nextDouble()*2.5+2.0;
				double splitPoint = rand.nextDouble()*0.05+0.0001;
				double roundness1 = rand.nextDouble()*0.0001;
				double staking = rand.nextDouble()*0.1+0.05;
				double roundness2 = rand.nextDouble()*0.0001;
				
				pstmt.setInt(1, id++);
				pstmt.setString(2, time);
				pstmt.setString(3, type);
				pstmt.setString(4, result);
				pstmt.setDouble(5, exDiameter1);
				pstmt.setDouble(6, exDiameter2);
				pstmt.setDouble(7, exDiameter3);
				pstmt.setDouble(8, whole);
				pstmt.setDouble(9, height);
				pstmt.setDouble(10, splitPoint);
				pstmt.setDouble(11, roundness1);
				pstmt.setDouble(12, staking);
				pstmt.setDouble(13, roundness2);
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