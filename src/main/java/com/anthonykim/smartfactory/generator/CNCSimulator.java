package com.anthonykim.smartfactory.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import com.anthonykim.smartfactory.imdg.db.ConnectionManager;
import com.anthonykim.smartfactory.imdg.db.MySQLConnectionManager;
import com.anthonykim.smartfactory.imdg.hazelcast.SmartFactoryIMDG;
import com.anthonykim.smartfactory.imdg.table.CNC;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class CNCSimulator implements Runnable {
	private int machineNo;
	private int writeMode;
	private String query;
	
	public final static int WRITE_DB = 0x01;
	public final static int WRITE_IMDG = 0x02;

	public CNCSimulator(int machineNo, int writeMode) {
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
	
	private void pushDatabase() {
		ConnectionManager connMgr = new MySQLConnectionManager();
		Connection conn = connMgr.getConnection();
		PreparedStatement pstmt = null;
		
		switch (machineNo) {
		case SmartFactoryIMDG.DN_1_01: query = "insert into SensorDB.DN_1_01(id, location, proNo, seqNo) values(?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_02: query = "insert into SensorDB.DN_1_02(id, location, proNo, seqNo) values(?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_03: query = "insert into SensorDB.DN_1_03(id, location, proNo, seqNo) values(?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_04: query = "insert into SensorDB.DN_1_04(id, location, proNo, seqNo) values(?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_05: query = "insert into SensorDB.DN_1_05(id, location, proNo, seqNo) values(?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_06: query = "insert into SensorDB.DN_1_06(id, location, proNo, seqNo) values(?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_07: query = "insert into SensorDB.DN_1_07(id, location, proNo, seqNo) values(?,?,?,?);"; break;
		case SmartFactoryIMDG.DN_1_08: query = "insert into SensorDB.DN_1_08(id, location, proNo, seqNo) values(?,?,?,?);"; break;
		}
		
		int id = 0;
		int proNo = 0;
		int seqNo = 0;
		String location = "(100, 100)";

		try {
			pstmt = conn.prepareStatement(query);
			while (proNo < SmartFactoryIMDG.MAX_RECORD) {
				pstmt.setInt(1, id++);
				pstmt.setString(2, location);
				pstmt.setInt(3, proNo++);
				pstmt.setInt(4, seqNo++);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabase(connMgr, conn, pstmt, null);
		}
	}
	
	private CNC getNextCNC(MultiMap<Integer, Object> database) {
		Collection<Object> column = null;
		
		switch (machineNo) {
		case SmartFactoryIMDG.DN_1_01: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_01); break;
		case SmartFactoryIMDG.DN_1_02: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_02); break;
		case SmartFactoryIMDG.DN_1_03: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_03); break;
		case SmartFactoryIMDG.DN_1_04: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_04); break;
		case SmartFactoryIMDG.DN_1_05: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_05); break;
		case SmartFactoryIMDG.DN_1_06: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_06); break;
		case SmartFactoryIMDG.DN_1_07: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_07); break;
		case SmartFactoryIMDG.DN_1_08: column = database.get(SmartFactoryIMDG.NEXT_ID_DN_1_08); break;
		}
		
		Iterator<Object> iterator = column.iterator();
		if (iterator.hasNext())
			return ((CNC) iterator.next());
		
		return null;
	}

	private void pushIMDG() {
		ClientConfig clientConfig = new ClientConfig();
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		MultiMap<Integer, Object> database = client.getMultiMap("SensorDB");
				
		CNC nextCNC = getNextCNC(database);
		
		int id = 0;
		int proNo = 0;
		int seqNo = 0;
		String location ="(100, 100)";
		
		if (nextCNC != null) {
			id = nextCNC.getId() + 1;
			proNo = nextCNC.getProNo() + 1;
			seqNo = nextCNC.getSeqNo() + 1;
		}
		while (true) {
			//System.out.println(id + ":" + proNo + ":" + seqNo);
			database.put(machineNo, new CNC(id++, location, proNo++, seqNo++, CNC.NOT_SYNCHED));
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