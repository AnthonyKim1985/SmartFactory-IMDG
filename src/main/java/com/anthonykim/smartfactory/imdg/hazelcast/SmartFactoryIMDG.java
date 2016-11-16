package com.anthonykim.smartfactory.imdg.hazelcast;

import com.anthonykim.smartfactory.imdg.db.ConnectionManager;
import com.anthonykim.smartfactory.imdg.db.MySQLConnectionManager;
import com.anthonykim.smartfactory.imdg.table.*;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapConfig.EvictionPolicy;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;


public class SmartFactoryIMDG implements DistributedObjectListener {
	private HazelcastInstance hzInstance;
	private MultiMap<Integer, Object> database;
	private MultiMap<Integer, Object> updateTarget;
	private IMDGIndexTable indexTable;
	private DBEntryListener litener;
	private String dbName;

	public final static int DN_1_01 = 0x00000001; // CNC
	public final static int DN_1_02 = 0x00000002; // CNC
	public final static int DN_1_03 = 0x00000004; // CNC
	public final static int DN_1_04 = 0x00000008; // CNC
	public final static int DN_1_05 = 0x00000010; // CNC
	public final static int DN_1_06 = 0x00000020; // CNC
	public final static int DN_1_07 = 0x00000040; // CNC
	public final static int DN_1_08 = 0x00000080; // CNC
	public final static int DN_1_09 = 0x00000100; // 열처리
	public final static int DN_1_11 = 0x00000200; // 랙전조
	public final static int DN_1_12 = 0x00000400; // 랙전조
	public final static int DN_1_13 = 0x00000800; // 랙전조
	public final static int DN_1_14 = 0x00001000; // 자동세척기
	public final static int DN_1_15 = 0x00002000; // 교정 & 구면연마기
	public final static int DN_1_19 = 0x00004000; // 자동검사

	public final static int NEXT_ID_DN_1_01 = 0x00010000; // CNC
	public final static int NEXT_ID_DN_1_02 = 0x00020000; // CNC
	public final static int NEXT_ID_DN_1_03 = 0x00040000; // CNC
	public final static int NEXT_ID_DN_1_04 = 0x00080000; // CNC
	public final static int NEXT_ID_DN_1_05 = 0x00100000; // CNC
	public final static int NEXT_ID_DN_1_06 = 0x00200000; // CNC
	public final static int NEXT_ID_DN_1_07 = 0x00400000; // CNC
	public final static int NEXT_ID_DN_1_08 = 0x00800000; // CNC
	public final static int NEXT_ID_DN_1_09 = 0x01000000; // 열처리
	public final static int NEXT_ID_DN_1_11 = 0x02000000; // 랙전조
	public final static int NEXT_ID_DN_1_12 = 0x04000000; // 랙전조
	public final static int NEXT_ID_DN_1_13 = 0x08000000; // 랙전조
	public final static int NEXT_ID_DN_1_14 = 0x10000000; // 자동세척기
	public final static int NEXT_ID_DN_1_15 = 0x20000000; // 교정 & 구면연마기
	public final static int NEXT_ID_DN_1_19 = 0x40000000; // 자동검사
	public final static int INDEX_TABLE = 0xFFFFFFFF;

	public final static Integer LOCK_KEY = 0xFF850628;
	public final static int MAX_RECORD = 3000000;
	public final static String UPDATE_TARGET = "UPDATE_DB";


	// DN_1_01 : CNC 01
	private IMap<Integer, CNC> idMapCNC01;
	private MultiMap<String, CNC> locationMMapCNC01;
	private MultiMap<Integer, CNC> proNoMMapCNC01;
	private MultiMap<Integer, CNC> seqNoMMapCNC01;

	// DN_1_02 : CNC 02
	private IMap<Integer, CNC> idMapCNC02;
	private MultiMap<String, CNC> locationMMapCNC02;
	private MultiMap<Integer, CNC> proNoMMapCNC02;
	private MultiMap<Integer, CNC> seqNoMMapCNC02;

	// DN_1_03 : CNC 03
	private IMap<Integer, CNC> idMapCNC03;
	private MultiMap<String, CNC> locationMMapCNC03;
	private MultiMap<Integer, CNC> proNoMMapCNC03;
	private MultiMap<Integer, CNC> seqNoMMapCNC03;

	// DN_1_04 : CNC 04
	private IMap<Integer, CNC> idMapCNC04;
	private MultiMap<String, CNC> locationMMapCNC04;
	private MultiMap<Integer, CNC> proNoMMapCNC04;
	private MultiMap<Integer, CNC> seqNoMMapCNC04;

	// DN_1_05 : CNC 05
	private IMap<Integer, CNC> idMapCNC05;
	private MultiMap<String, CNC> locationMMapCNC05;
	private MultiMap<Integer, CNC> proNoMMapCNC05;
	private MultiMap<Integer, CNC> seqNoMMapCNC05;

	// DN_1_06 : CNC 06
	private IMap<Integer, CNC> idMapCNC06; 
	private MultiMap<String, CNC> locationMMapCNC06;
	private MultiMap<Integer, CNC> proNoMMapCNC06;
	private MultiMap<Integer, CNC> seqNoMMapCNC06;

	// DN_1_07 : CNC 07
	private IMap<Integer, CNC> idMapCNC07;
	private MultiMap<String, CNC> locationMMapCNC07;
	private MultiMap<Integer, CNC> proNoMMapCNC07;
	private MultiMap<Integer, CNC> seqNoMMapCNC07;

	// DN_1_08 : CNC 08
	private IMap<Integer, CNC> idMapCNC08;
	private MultiMap<String, CNC> locationMMapCNC08;
	private MultiMap<Integer, CNC> proNoMMapCNC08;
	private MultiMap<Integer, CNC> seqNoMMapCNC08;

	// DN_1_09 : HEAT
	private IMap<Integer, HEAT> idMapHEAT;
	private MultiMap<Double, HEAT> voltMMapHEAT;
	private MultiMap<Double, HEAT> ampereMMapHEAT;
	private MultiMap<Double, HEAT> eOutputMMapHEAT;
	private MultiMap<Integer, HEAT> amountMMapHEAT;
	private MultiMap<Double, HEAT> temperatureMMapHEAT;
	private MultiMap<Double, HEAT> flowMMapHEAT;
	private MultiMap<Integer, HEAT> rpmMMapHEAT;
	private MultiMap<Integer, HEAT> workTimeMMapHEAT;

	// DN_1_11 : RACK 01
	private IMap<Integer, RACK> idMapRACK01;
	private MultiMap<Integer, RACK> diseMMapRACK01;
	private MultiMap<Integer, RACK> countMMapRACK01;
	private MultiMap<Double, RACK> temperatureMMapRACK01;
	private MultiMap<Integer, RACK> countSumMMapRACK01;

	// DN_1_12 : RACK 02
	private IMap<Integer, RACK> idMapRACK02;
	private MultiMap<Integer, RACK> diseMMapRACK02;
	private MultiMap<Integer, RACK> countMMapRACK02;
	private MultiMap<Double, RACK> temperatureMMapRACK02;
	private MultiMap<Integer, RACK> countSumMMapRACK02;

	// DN_1_13 : RACK 03
	private IMap<Integer, RACK> idMapRACK03;
	private MultiMap<Integer, RACK> diseMMapRACK03;
	private MultiMap<Integer, RACK> countMMapRACK03;
	private MultiMap<Double, RACK> temperatureMMapRACK03;
	private MultiMap<Integer, RACK> countSumMMapRACK03;

	// DN_1_14 : CLEAN
	private IMap<Integer, CLEAN> idMapCLEAN;
	private MultiMap<Double, CLEAN> output1MMapCLEAN;
	private MultiMap<Double, CLEAN> outAngleMMapCLEAN;
	private MultiMap<Double, CLEAN> correctAngleMMapCLEAN;	
	private MultiMap<Integer, CLEAN> correctPartMMapCLEAN;
	private MultiMap<Integer, CLEAN> dataCountMMapCLEAN;
	private MultiMap<Double, CLEAN> output2MMapCLEAN;
	private MultiMap<Double, CLEAN> result1MMapCLEAN;
	private MultiMap<Double, CLEAN> result2MMapCLEAN;
	private MultiMap<Double, CLEAN> result3MMapCLEAN;
	private MultiMap<Double, CLEAN> correctionMMapCLEAN;
	private MultiMap<Integer, CLEAN> fairAmountMMapCLEAN;
	private MultiMap<Integer, CLEAN> faultAmountMMapCLEAN;

	// DN_1_15 : POLISH
	private IMap<Integer, POLISH> idMapPOLISH;
	private MultiMap<Double, POLISH> wholeLengthMMapPOLISH;
	private MultiMap<Double, POLISH> shakingMMapPOLISH;

	// DN_1_19 : INSPECTION
	private IMap<Integer, INSPECTION> idMapINSPECTION;
	private MultiMap<String, INSPECTION> timeMMapINSPECTION;
	private MultiMap<String, INSPECTION> typeMMapINSPECTION;
	private MultiMap<String, INSPECTION> resultMMapINSPECTION;
	private MultiMap<Double, INSPECTION> exDiameter1MMapINSPECTION;
	private MultiMap<Double, INSPECTION> exDiameter2MMapINSPECTION;
	private MultiMap<Double, INSPECTION> exDiameter3MMapINSPECTION;
	private MultiMap<Double, INSPECTION> wholeMMapINSPECTION;
	private MultiMap<Double, INSPECTION> heightMMapINSPECTION;
	private MultiMap<Double, INSPECTION> splitPointMMapINSPECTION;
	private MultiMap<Double, INSPECTION> roundness1MMapINSPECTION;
	private MultiMap<Double, INSPECTION> stakingMMapINSPECTION;
	private MultiMap<Double, INSPECTION> roundness2MMapINSPECTION;

	public SmartFactoryIMDG(String dbName) {
		this.dbName = dbName;
		this.hzInstance = null;
		this.database = null;
	}

	public void initIMDG() {
		Config config = new Config();
		config.setProperty("hazelcast.elastic.memory.enabled", "true");
		config.setProperty("hazelcast.elastic.memory.total.size", "10G");
		config.setProperty("hazelcast.elastic.memory.chunk.size", "1K");
		config.setProperty("hazelcast.elastic.memory.unsafe.enabled", "true");

		MapConfig mapConfig = new MapConfig();
		mapConfig.setInMemoryFormat(InMemoryFormat.OFFHEAP);
		mapConfig.setBackupCount(0);
		mapConfig.setEvictionPolicy(EvictionPolicy.LRU);
		mapConfig.setEvictionPercentage(2);
		
		MaxSizeConfig mxSizeCfg = new MaxSizeConfig(Integer.MAX_VALUE, MaxSizeConfig.MaxSizePolicy.PER_NODE);
		mapConfig.setMaxSizeConfig(mxSizeCfg);
		mapConfig.setName(dbName);

		hzInstance = Hazelcast.newHazelcastInstance(config);

		hzInstance.addDistributedObjectListener(this);
		database = hzInstance.getMultiMap(dbName);
		updateTarget = hzInstance.getMultiMap(UPDATE_TARGET);

		litener = new DBEntryListener(hzInstance);
		database.addEntryListener(litener, true);

		Collection<DistributedObject> distributedObjects = hzInstance.getDistributedObjects();
		for (DistributedObject distributedobject : distributedObjects)
			System.out.println(distributedobject.getName() + "," + distributedobject.getId());

		// DN_1_01 : CNC 01
		idMapCNC01 = hzInstance.getMap("idMapCNC01");
		locationMMapCNC01 = hzInstance.getMultiMap("locationMMapCNC01");
		proNoMMapCNC01 = hzInstance.getMultiMap("proNoMMapCNC01");
		seqNoMMapCNC01 = hzInstance.getMultiMap("seqNoMMapCNC01");

		// DN_1_02 : CNC 02
		idMapCNC02 = hzInstance.getMap("idMapCNC02");
		locationMMapCNC02 = hzInstance.getMultiMap("locationMMapCNC02");
		proNoMMapCNC02 = hzInstance.getMultiMap("proNoMMapCNC02");
		seqNoMMapCNC02 = hzInstance.getMultiMap("seqNoMMapCNC02");

		// DN_1_03 : CNC 03
		idMapCNC03 = hzInstance.getMap("idMapCNC03");
		locationMMapCNC03 = hzInstance.getMultiMap("locationMMapCNC03");
		proNoMMapCNC03 = hzInstance.getMultiMap("proNoMMapCNC03");
		seqNoMMapCNC03 = hzInstance.getMultiMap("seqNoMMapCNC03");

		// DN_1_04 : CNC 04
		idMapCNC04 = hzInstance.getMap("idMapCNC04");
		locationMMapCNC04 = hzInstance.getMultiMap("locationMMapCNC04");
		proNoMMapCNC04 = hzInstance.getMultiMap("proNoMMapCNC04");
		seqNoMMapCNC04 = hzInstance.getMultiMap("seqNoMMapCNC04");

		// DN_1_05 : CNC 05
		idMapCNC05 = hzInstance.getMap("idMapCNC05");
		locationMMapCNC05 = hzInstance.getMultiMap("locationMMapCNC05");
		proNoMMapCNC05 = hzInstance.getMultiMap("proNoMMapCNC05");
		seqNoMMapCNC05 = hzInstance.getMultiMap("seqNoMMapCNC05");

		// DN_1_06 : CNC 06
		idMapCNC06 = hzInstance.getMap("idMapCNC06");
		locationMMapCNC06 = hzInstance.getMultiMap("locationMMapCNC06");
		proNoMMapCNC06 = hzInstance.getMultiMap("proNoMMapCNC06");
		seqNoMMapCNC06 = hzInstance.getMultiMap("seqNoMMapCNC06");

		// DN_1_07 : CNC 07
		idMapCNC07 = hzInstance.getMap("idMapCNC07");
		locationMMapCNC07 = hzInstance.getMultiMap("locationMMapCNC07");
		proNoMMapCNC07 = hzInstance.getMultiMap("proNoMMapCNC07");
		seqNoMMapCNC07 = hzInstance.getMultiMap("seqNoMMapCNC07");

		// DN_1_08 : CNC 08
		idMapCNC08 = hzInstance.getMap("idMapCNC08");
		locationMMapCNC08 = hzInstance.getMultiMap("locationMMapCNC08");
		proNoMMapCNC08 = hzInstance.getMultiMap("proNoMMapCNC08");
		seqNoMMapCNC08 = hzInstance.getMultiMap("seqNoMMapCNC08");

		// DN_1_09 : HEAT
		idMapHEAT = hzInstance.getMap("idMapHEAT");
		voltMMapHEAT = hzInstance.getMultiMap("voltMMapHEAT");
		ampereMMapHEAT = hzInstance.getMultiMap("ampereMMapHEAT");
		eOutputMMapHEAT = hzInstance.getMultiMap("eOutputMMapHEAT");
		amountMMapHEAT = hzInstance.getMultiMap("amountMMapHEAT");
		temperatureMMapHEAT = hzInstance.getMultiMap("temperatureMMapHEAT");
		flowMMapHEAT = hzInstance.getMultiMap("flowMMapHEAT");
		rpmMMapHEAT = hzInstance.getMultiMap("rpmMMapHEAT");
		workTimeMMapHEAT = hzInstance.getMultiMap("workTimeMMapHEAT");

		// DN_1_11 : RACK 01
		idMapRACK01 = hzInstance.getMap("idMapRACK01");
		diseMMapRACK01 = hzInstance.getMultiMap("diseMMapRACK01");
		countMMapRACK01 = hzInstance.getMultiMap("countMMapRACK01");
		temperatureMMapRACK01 = hzInstance.getMultiMap("temperatureMMapRACK01");
		countSumMMapRACK01 = hzInstance.getMultiMap("countSumMMapRACK01");

		// DN_1_12 : RACK 02
		idMapRACK02 = hzInstance.getMap("idMapRACK02");
		diseMMapRACK02 = hzInstance.getMultiMap("diseMMapRACK02");
		countMMapRACK02 = hzInstance.getMultiMap("countMMapRACK02");
		temperatureMMapRACK02 = hzInstance.getMultiMap("temperatureMMapRACK02");
		countSumMMapRACK02 = hzInstance.getMultiMap("countSumMMapRACK02");

		// DN_1_13 : RACK 03
		idMapRACK03 = hzInstance.getMap("idMapRACK03");
		diseMMapRACK03 = hzInstance.getMultiMap("diseMMapRACK03");
		countMMapRACK03 = hzInstance.getMultiMap("countMMapRACK03");
		temperatureMMapRACK03 = hzInstance.getMultiMap("temperatureMMapRACK03");
		countSumMMapRACK03 = hzInstance.getMultiMap("countSumMMapRACK03");		

		// DN_1_14 : CLEAN
		idMapCLEAN = hzInstance.getMap("idMapCLEAN");
		output1MMapCLEAN = hzInstance.getMultiMap("output1MMapCLEAN");
		outAngleMMapCLEAN = hzInstance.getMultiMap("outAngleMMapCLEAN");
		correctAngleMMapCLEAN = hzInstance.getMultiMap("correctAngleCLEAN");	
		correctPartMMapCLEAN = hzInstance.getMultiMap("correctPartMMapCLEAN");
		dataCountMMapCLEAN = hzInstance.getMultiMap("dataCountMMapCLEAN");
		output2MMapCLEAN = hzInstance.getMultiMap("output2MMapCLEAN");
		result1MMapCLEAN = hzInstance.getMultiMap("result1MMapCLEAN");
		result2MMapCLEAN = hzInstance.getMultiMap("result2MMapCLEAN");
		result3MMapCLEAN = hzInstance.getMultiMap("result3MMapCLEAN");
		correctionMMapCLEAN = hzInstance.getMultiMap("correctionMMapCLEAN");
		fairAmountMMapCLEAN = hzInstance.getMultiMap("fairAmountMMapCLEAN");
		faultAmountMMapCLEAN = hzInstance.getMultiMap("faultAmountMMapCLEAN");

		// DN_1_15 : POLISH
		idMapPOLISH = hzInstance.getMap("idMapPOLISH");
		wholeLengthMMapPOLISH = hzInstance.getMultiMap("wholeLengthMMapPOLISH");
		shakingMMapPOLISH = hzInstance.getMultiMap("shakingMMapPOLISH");

		// DN_1_19 : INSPECTION
		idMapINSPECTION = hzInstance.getMap("idMapINSPECTION");
		timeMMapINSPECTION = hzInstance.getMultiMap("timeMMapINSPECTION");
		typeMMapINSPECTION = hzInstance.getMultiMap("typeMMapINSPECTION");
		resultMMapINSPECTION = hzInstance.getMultiMap("resultMMapINSPECTION");
		exDiameter1MMapINSPECTION = hzInstance.getMultiMap("exDiameter1MMapINSPECTION");
		exDiameter2MMapINSPECTION = hzInstance.getMultiMap("exDiameter2MMapINSPECTION");
		exDiameter3MMapINSPECTION = hzInstance.getMultiMap("exDiameter3MMapINSPECTION");
		wholeMMapINSPECTION = hzInstance.getMultiMap("wholeMMapINSPECTION");
		heightMMapINSPECTION = hzInstance.getMultiMap("heightMMapINSPECTION");
		splitPointMMapINSPECTION = hzInstance.getMultiMap("splitPointMMapINSPECTION");
		roundness1MMapINSPECTION = hzInstance.getMultiMap("roundness1MMapINSPECTION");
		stakingMMapINSPECTION = hzInstance.getMultiMap("stakingMMapINSPECTION");
		roundness2MMapINSPECTION = hzInstance.getMultiMap("roundness2MMapINSPECTION");
	}

	@Override
	public void distributedObjectCreated(DistributedObjectEvent event) {
		DistributedObject instance = event.getDistributedObject();
		System.out.println("Created NAME: " + instance.getName() + ", ID: " + instance.getId());	
	}

	@Override
	public void distributedObjectDestroyed(DistributedObjectEvent event) {
		DistributedObject instance = event.getDistributedObject();
		System.out.println("Destroyed NAME: " + instance.getName() + ", ID: " + instance.getId());		
	}

	public IMDGIndexTable getIndexTable() {
		return indexTable;
	}

	public void setIndexTable(IMDGIndexTable indexTable) {
		this.indexTable = indexTable;
	}

	public HazelcastInstance getHzInstance() {
		return hzInstance;
	}

	public MultiMap<Integer, Object> getUpdateTarget() {
		return updateTarget;
	}

	public void setUpdateTarget(MultiMap<Integer, Object> updateTarget) {
		this.updateTarget = updateTarget;
	}

	public MultiMap<Integer, Object> getDatabase() {
		return database;
	}

	public void setDatabase(MultiMap<Integer, Object> database) {
		this.database = database;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void fetchCNC01() {
		ConnectionManager connMgr = new MySQLConnectionManager();
		Connection conn = connMgr.getConnection();

		ClientConfig clientConfig = new ClientConfig();
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		//MultiMap<Integer, Object> database = client.getMultiMap("SensorDB");

		try {
			loadCNC(client, conn, DN_1_01);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabase(connMgr, conn, null, null);
		}
		System.err.println("All data have been loaded successfully.");
	}

	public void fetchDatabase() {
		ConnectionManager connMgr = new MySQLConnectionManager();
		Connection conn = connMgr.getConnection();

		ClientConfig clientConfig = new ClientConfig();
		HazelcastInstance hzInstance = HazelcastClient.newHazelcastClient(clientConfig);

		try {
			for (int machineNo = 0x00000001; machineNo <= 0x00004000; machineNo <<= 0x00000001) {
				switch (machineNo) {
				case DN_1_01:
				case DN_1_02:
				case DN_1_03:
				case DN_1_04:
				case DN_1_05:
				case DN_1_06:
				case DN_1_07:
				case DN_1_08:
					loadCNC(hzInstance, conn, machineNo); 
					break;
				case DN_1_09:
					loadHEAT(hzInstance, conn);
					break;
				case DN_1_11:
				case DN_1_12:
				case DN_1_13:
					loadRACK(hzInstance, conn, machineNo);
					break;
				case DN_1_14:
					loadCLEAN(hzInstance, conn);
					break;
				case DN_1_15:
					loadPOLISH(hzInstance, conn);
					break;
				case DN_1_19:
					loadINSPECTION(hzInstance, conn);
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabase(connMgr, conn, null, null);
		}

		System.err.println("All data have been loaded successfully.");
	}

	private void loadCNC(HazelcastInstance hzInstance, Connection conn, int machineNo) throws SQLException {	
		int nextIdKey = 0;
		String query = null;

		IMap<Integer, CNC> idMapCNC = null;
		MultiMap<String, CNC> locationMMapCNC = null;
		MultiMap<Integer, CNC> proNoMMapCNC = null;
		MultiMap<Integer, CNC> seqNoMMapCNC = null;

		switch (machineNo) {
		case DN_1_01:
			nextIdKey = NEXT_ID_DN_1_01;
			idMapCNC = hzInstance.getMap("idMapCNC01");
			locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC01");
			proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC01");
			seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC01");
			query = "select id, location, proNo, seqNo from SensorDB.DN_1_01;"; 
			break;
		case DN_1_02:
			nextIdKey = NEXT_ID_DN_1_02;
			idMapCNC = hzInstance.getMap("idMapCNC02");
			locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC02");
			proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC02");
			seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC02");
			query = "select id, location, proNo, seqNo from SensorDB.DN_1_02;"; 
			break;
		case DN_1_03:
			nextIdKey = NEXT_ID_DN_1_03;
			idMapCNC = hzInstance.getMap("idMapCNC03");
			locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC03");
			proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC03");
			seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC03");
			query = "select id, location, proNo, seqNo from SensorDB.DN_1_03;"; 
			break;
		case DN_1_04:
			nextIdKey = NEXT_ID_DN_1_04;
			idMapCNC = hzInstance.getMap("idMapCNC04");
			locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC04");
			proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC04");
			seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC04");
			query = "select id, location, proNo, seqNo from SensorDB.DN_1_04;"; 
			break;
		case DN_1_05:
			nextIdKey = NEXT_ID_DN_1_05;
			idMapCNC = hzInstance.getMap("idMapCNC05");
			locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC05");
			proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC05");
			seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC05");
			query = "select id, location, proNo, seqNo from SensorDB.DN_1_05;"; 
			break;
		case DN_1_06:
			nextIdKey = NEXT_ID_DN_1_06;
			idMapCNC = hzInstance.getMap("idMapCNC06");
			locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC06");
			proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC06");
			seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC06");
			query = "select id, location, proNo, seqNo from SensorDB.DN_1_06;"; 
			break;
		case DN_1_07:
			nextIdKey = NEXT_ID_DN_1_07;
			idMapCNC = hzInstance.getMap("idMapCNC07");
			locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC07");
			proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC07");
			seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC07");
			query = "select id, location, proNo, seqNo from SensorDB.DN_1_07;"; 
			break;
		case DN_1_08:
			nextIdKey = NEXT_ID_DN_1_08;
			idMapCNC = hzInstance.getMap("idMapCNC08");
			locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC08");
			proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC08");
			seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC08");
			query = "select id, location, proNo, seqNo from SensorDB.DN_1_08;"; 
			break;
		default:
			return;
		}

		MultiMap<Integer, Object> database = hzInstance.getMultiMap("SensorDB");
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		int nextId = 0;
		CNC nextCNC = null;

		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			String location = resultSet.getString(2);
			int proNo = resultSet.getInt(3);
			int seqNo = resultSet.getInt(4);

			CNC columnCNC = new CNC(id, location, proNo, seqNo, CNC.SYNCHED);
			if (id > nextId) {
				nextId = id;
				nextCNC = columnCNC;
			}

			idMapCNC.put(id, columnCNC);
			locationMMapCNC.put(location, columnCNC);
			proNoMMapCNC.put(proNo, columnCNC);
			seqNoMMapCNC.put(seqNo, columnCNC);
			database.put(machineNo, columnCNC);
		}
		database.put(nextIdKey, nextCNC);
		System.err.println(litener.getTableName(machineNo) + " is loaded.");
		closeDatabase(null, null, pstmt, resultSet);
	}

	private void loadHEAT(HazelcastInstance hzInstance, Connection conn) throws SQLException {
		String query = "select id, volt, ampere, eOutput, amount, temperature, flow, rpm, workTime from SensorDB.DN_1_09;";

		IMap<Integer, HEAT> idMapHEAT = hzInstance.getMap("idMapHEAT");
		MultiMap<Double, HEAT> voltMMapHEAT = hzInstance.getMultiMap("voltMMapHEAT");
		MultiMap<Double, HEAT> ampereMMapHEAT = hzInstance.getMultiMap("ampereMMapHEAT");
		MultiMap<Double, HEAT> eOutputMMapHEAT = hzInstance.getMultiMap("eOutputMMapHEAT");
		MultiMap<Integer, HEAT> amountMMapHEAT = hzInstance.getMultiMap("amountMMapHEAT");
		MultiMap<Double, HEAT> temperatureMMapHEAT = hzInstance.getMultiMap("temperatureMMapHEAT");
		MultiMap<Double, HEAT> flowMMapHEAT = hzInstance.getMultiMap("flowMMapHEAT");
		MultiMap<Integer, HEAT> rpmMMapHEAT = hzInstance.getMultiMap("rpmMMapHEAT");
		MultiMap<Integer, HEAT> workTimeMMapHEAT = hzInstance.getMultiMap("workTimeMMapHEAT");

		MultiMap<Integer, Object> database = hzInstance.getMultiMap("SensorDB");
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		int nextId = 0;
		HEAT nextHEAT = null;

		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			double volt = resultSet.getDouble(2);
			double ampere = resultSet.getDouble(3);
			double eOutput = resultSet.getDouble(4);
			int amount = resultSet.getInt(5);
			double temperature = resultSet.getDouble(6);
			double flow = resultSet.getDouble(7);
			int rpm = resultSet.getInt(8);
			int workTime = resultSet.getInt(9);

			HEAT columnHEAT = new HEAT(id, volt, ampere, eOutput, amount, temperature, flow, rpm, workTime, HEAT.SYNCHED);
			if (id > nextId) {
				nextId = id;
				nextHEAT = columnHEAT;
			}

			idMapHEAT.put(id, columnHEAT);
			voltMMapHEAT.put(volt, columnHEAT);
			ampereMMapHEAT.put(ampere, columnHEAT);
			eOutputMMapHEAT.put(eOutput, columnHEAT);
			amountMMapHEAT.put(amount, columnHEAT);
			temperatureMMapHEAT.put(temperature, columnHEAT);
			flowMMapHEAT.put(flow, columnHEAT);
			rpmMMapHEAT.put(rpm, columnHEAT);
			workTimeMMapHEAT.put(workTime, columnHEAT);

			database.put(DN_1_09, columnHEAT);
		}
		database.put(NEXT_ID_DN_1_09, nextHEAT);
		System.err.println(litener.getTableName(DN_1_09) + " is loaded.");
		closeDatabase(null, null, pstmt, resultSet);
	}

	private void loadRACK(HazelcastInstance hzInstance, Connection conn, int machineNo) throws SQLException {
		String query = null;

		IMap<Integer, RACK> idMapRACK = null;
		MultiMap<Integer, RACK> diseMMapRACK = null;
		MultiMap<Integer, RACK> countMMapRACK = null;
		MultiMap<Double, RACK> temperatureMMapRACK = null;
		MultiMap<Integer, RACK> countSumMMapRACK = null;

		int nextIdKey = 0;
		switch (machineNo) {
		case DN_1_11: 
			nextIdKey = NEXT_ID_DN_1_11; 
			idMapRACK = hzInstance.getMap("idMapRACK01");
			diseMMapRACK = hzInstance.getMultiMap("diseMMapRACK01");
			countMMapRACK = hzInstance.getMultiMap("countMMapRACK01");
			temperatureMMapRACK = hzInstance.getMultiMap("temperatureMMapRACK01");
			countSumMMapRACK = hzInstance.getMultiMap("countSumMMapRACK01");
			query = "select id, dise, count, temperature, countSum from SensorDB.DN_1_11;"; 
			break;
		case DN_1_12: 
			nextIdKey = NEXT_ID_DN_1_12; 
			idMapRACK = hzInstance.getMap("idMapRACK02");
			diseMMapRACK = hzInstance.getMultiMap("diseMMapRACK02");
			countMMapRACK = hzInstance.getMultiMap("countMMapRACK02");
			temperatureMMapRACK = hzInstance.getMultiMap("temperatureMMapRACK02");
			countSumMMapRACK = hzInstance.getMultiMap("countSumMMapRACK02");
			query = "select id, dise, count, temperature, countSum from SensorDB.DN_1_12;"; 
			break;
		case DN_1_13: 
			nextIdKey = NEXT_ID_DN_1_13; 
			idMapRACK = hzInstance.getMap("idMapRACK03");
			diseMMapRACK = hzInstance.getMultiMap("diseMMapRACK03");
			countMMapRACK = hzInstance.getMultiMap("countMMapRACK03");
			temperatureMMapRACK = hzInstance.getMultiMap("temperatureMMapRACK03");
			countSumMMapRACK = hzInstance.getMultiMap("countSumMMapRACK03");
			query = "select id, dise, count, temperature, countSum from SensorDB.DN_1_13;"; 
			break;

		}

		MultiMap<Integer, Object> database = hzInstance.getMultiMap("SensorDB");
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		int nextId = 0;
		RACK nextRACK = null;
		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			int dise = resultSet.getInt(2);
			int count = resultSet.getInt(3);
			double temperature = resultSet.getDouble(4);
			int countSum = resultSet.getInt(5);

			RACK columnRACK = new RACK(id, dise, count, temperature, countSum, RACK.SYNCHED);
			if (id > nextId) {
				nextId = id;
				nextRACK = columnRACK;
			}

			idMapRACK.put(id, columnRACK);
			diseMMapRACK.put(dise, columnRACK);
			countMMapRACK.put(count, columnRACK);
			temperatureMMapRACK.put(temperature, columnRACK);
			countSumMMapRACK.put(countSum, columnRACK);

			database.put(machineNo, columnRACK);
		}
		database.put(nextIdKey, nextRACK);
		System.err.println(litener.getTableName(machineNo) + " is loaded.");
		closeDatabase(null, null, pstmt, resultSet);
	}

	private void loadCLEAN(HazelcastInstance hzInstance, Connection conn) throws SQLException {
		String query = "select id, output1, outAngle, correctAngle, correctPart, dataCount, output2, result1, result2, result3, correction, fairAmount, faultAmount from SensorDB.DN_1_14;";

		IMap<Integer, CLEAN> idMapCLEAN = hzInstance.getMap("idMapCLEAN");
		MultiMap<Double, CLEAN> output1MMapCLEAN = hzInstance.getMultiMap("output1MMapCLEAN");
		MultiMap<Double, CLEAN> outAngleMMapCLEAN = hzInstance.getMultiMap("outAngleMMapCLEAN");
		MultiMap<Double, CLEAN> correctAngleMMapCLEAN = hzInstance.getMultiMap("correctAngleMMapCLEAN");
		MultiMap<Integer, CLEAN> correctPartMMapCLEAN = hzInstance.getMultiMap("correctPartMMapCLEAN");
		MultiMap<Integer, CLEAN> dataCountMMapCLEAN = hzInstance.getMultiMap("dataCountMMapCLEAN");
		MultiMap<Double, CLEAN> output2MMapCLEAN = hzInstance.getMultiMap("output2MMapCLEAN");
		MultiMap<Double, CLEAN> result1MMapCLEAN = hzInstance.getMultiMap("result1MMapCLEAN");
		MultiMap<Double, CLEAN> result2MMapCLEAN = hzInstance.getMultiMap("result2MMapCLEAN");
		MultiMap<Double, CLEAN> result3MMapCLEAN = hzInstance.getMultiMap("result3MMapCLEAN");
		MultiMap<Double, CLEAN> correctionMMapCLEAN = hzInstance.getMultiMap("correctionMMapCLEAN");
		MultiMap<Integer, CLEAN> fairAmountMMapCLEAN = hzInstance.getMultiMap("fairAmountMMapCLEAN");
		MultiMap<Integer, CLEAN> faultAmountMMapCLEAN = hzInstance.getMultiMap("faultAmountMMapCLEAN");

		MultiMap<Integer, Object> database = hzInstance.getMultiMap("SensorDB");
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		int nextId = 0;
		CLEAN nextCLEAN = null;

		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			double output1 = resultSet.getDouble(2);
			double outAngle = resultSet.getDouble(3);
			double correctAngle = resultSet.getDouble(4);
			int correctPart = resultSet.getInt(5);
			int dataCount = resultSet.getInt(6);
			double output2 = resultSet.getDouble(7);
			double result1 = resultSet.getDouble(8);
			double result2 = resultSet.getDouble(9);
			double result3 = resultSet.getDouble(10);
			double correction = resultSet.getDouble(11);
			int fairAmount = resultSet.getInt(12);
			int faultAmount = resultSet.getInt(13);

			CLEAN columnCLEAN = new CLEAN(id, output1, outAngle, correctAngle, correctPart,
					dataCount, output2, result1, result2, result3, correction, fairAmount, faultAmount, CLEAN.SYNCHED);
			if (id > nextId) {
				nextId = id;
				nextCLEAN = columnCLEAN;
			}

			idMapCLEAN.put(id, columnCLEAN);
			output1MMapCLEAN.put(output1, columnCLEAN);
			outAngleMMapCLEAN.put(outAngle, columnCLEAN);
			correctAngleMMapCLEAN.put(correctAngle, columnCLEAN);
			correctPartMMapCLEAN.put(correctPart, columnCLEAN);
			dataCountMMapCLEAN.put(dataCount, columnCLEAN);
			output2MMapCLEAN.put(output2, columnCLEAN);
			result1MMapCLEAN.put(result1, columnCLEAN);
			result2MMapCLEAN.put(result2, columnCLEAN);
			result3MMapCLEAN.put(result3, columnCLEAN);
			correctionMMapCLEAN.put(correction, columnCLEAN);
			fairAmountMMapCLEAN.put(fairAmount, columnCLEAN);
			faultAmountMMapCLEAN.put(faultAmount, columnCLEAN);

			database.put(DN_1_14, columnCLEAN);
		}
		database.put(NEXT_ID_DN_1_14, nextCLEAN);
		System.err.println(litener.getTableName(DN_1_14) + " is loaded.");
		closeDatabase(null, null, pstmt, resultSet);
	}

	private void loadPOLISH(HazelcastInstance hzInstance, Connection conn) throws SQLException {
		String query = "select id, wholeLength, shaking from SensorDB.DN_1_15;";

		IMap<Integer, POLISH> idMapPOLISH = hzInstance.getMap("idMapPOLISH");
		MultiMap<Double, POLISH> wholeLengthMMapPOLISH = hzInstance.getMultiMap("wholeLengthMMapPOLISH");
		MultiMap<Double, POLISH> shakingMMapPOLISH = hzInstance.getMultiMap("shakingMMapPOLISH");

		MultiMap<Integer, Object> database = hzInstance.getMultiMap("SensorDB");
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		int nextId = 0;
		POLISH nextPOLISH = null;

		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			double wholeLength = resultSet.getDouble(2);
			double shaking = resultSet.getDouble(3);

			POLISH columnPOLISH = new POLISH(id, wholeLength, shaking, POLISH.SYNCHED);
			if (id > nextId) {
				nextId = id;
				nextPOLISH = columnPOLISH;
			}

			idMapPOLISH.put(id, columnPOLISH);
			wholeLengthMMapPOLISH.put(wholeLength, columnPOLISH);
			shakingMMapPOLISH.put(shaking, columnPOLISH);

			database.put(DN_1_15, columnPOLISH);
		}
		database.put(NEXT_ID_DN_1_15, nextPOLISH);
		System.err.println(litener.getTableName(DN_1_15) + " is loaded.");
		closeDatabase(null, null, pstmt, resultSet);
	}

	private void loadINSPECTION(HazelcastInstance hzInstance, Connection conn) throws SQLException {
		String query = "select id, time, type, result, exDiameter1, exDiameter2, exDiameter3, whole, height, splitPoint, roundness1, staking, roundness2 from SensorDB.DN_1_19;";

		IMap<Integer, INSPECTION> idMapINSPECTION = hzInstance.getMap("idMapINSPECTION");
		MultiMap<String, INSPECTION> timeMMapINSPECTION = hzInstance.getMultiMap("timeMMapINSPECTION");
		MultiMap<String, INSPECTION> typeMMapINSPECTION = hzInstance.getMultiMap("typeMMapINSPECTION");
		MultiMap<String, INSPECTION> resultMMapINSPECTION = hzInstance.getMultiMap("resultMMapINSPECTION");
		MultiMap<Double, INSPECTION> exDiameter1MMapINSPECTION = hzInstance.getMultiMap("exDiameter1MMapINSPECTION");
		MultiMap<Double, INSPECTION> exDiameter2MMapINSPECTION = hzInstance.getMultiMap("exDiameter2MMapINSPECTION");
		MultiMap<Double, INSPECTION> exDiameter3MMapINSPECTION = hzInstance.getMultiMap("exDiameter3MMapINSPECTION");
		MultiMap<Double, INSPECTION> wholeMMapINSPECTION = hzInstance.getMultiMap("wholeMMapINSPECTION");
		MultiMap<Double, INSPECTION> heightMMapINSPECTION = hzInstance.getMultiMap("heightMMapINSPECTION");
		MultiMap<Double, INSPECTION> splitPointMMapINSPECTION = hzInstance.getMultiMap("splitPointMMapINSPECTION");
		MultiMap<Double, INSPECTION> roundness1MMapINSPECTION = hzInstance.getMultiMap("roundness1MMapINSPECTION");
		MultiMap<Double, INSPECTION> stakingMMapINSPECTION = hzInstance.getMultiMap("stakingMMapINSPECTION");
		MultiMap<Double, INSPECTION> roundness2MMapINSPECTION = hzInstance.getMultiMap("roundness2MMapINSPECTION");

		MultiMap<Integer, Object> database = hzInstance.getMultiMap("SensorDB");
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		int nextId = 0;
		INSPECTION nextINSPECTION = null;

		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			String time = resultSet.getString(2);
			String type = resultSet.getString(3);
			String result = resultSet.getString(4);
			double exDiameter1 = resultSet.getDouble(5);
			double exDiameter2 = resultSet.getDouble(6);
			double exDiameter3 = resultSet.getDouble(7);
			double whole = resultSet.getDouble(8);
			double height = resultSet.getDouble(9);
			double splitPoint = resultSet.getDouble(10);
			double roundness1 = resultSet.getDouble(11);
			double staking = resultSet.getDouble(12);
			double roundness2 = resultSet.getDouble(13);

			INSPECTION columnINSPECTION = new INSPECTION(id, time, type, result,
					exDiameter1, exDiameter2, exDiameter3, whole, height, 
					splitPoint, roundness1, staking, roundness2, INSPECTION.SYNCHED);

			if (id > nextId) {
				nextId = id;
				nextINSPECTION = columnINSPECTION;
			}

			idMapINSPECTION.put(id, columnINSPECTION);
			timeMMapINSPECTION.put(time, columnINSPECTION);
			typeMMapINSPECTION.put(type, columnINSPECTION);
			resultMMapINSPECTION.put(result, columnINSPECTION);
			exDiameter1MMapINSPECTION.put(exDiameter1, columnINSPECTION);
			exDiameter2MMapINSPECTION.put(exDiameter2, columnINSPECTION);
			exDiameter3MMapINSPECTION.put(exDiameter3, columnINSPECTION);
			wholeMMapINSPECTION.put(whole, columnINSPECTION);
			heightMMapINSPECTION.put(height, columnINSPECTION);
			splitPointMMapINSPECTION.put(splitPoint, columnINSPECTION);
			roundness1MMapINSPECTION.put(roundness1, columnINSPECTION);
			stakingMMapINSPECTION.put(staking, columnINSPECTION);
			roundness2MMapINSPECTION.put(roundness2, columnINSPECTION);

			database.put(DN_1_19, columnINSPECTION);
		}
		database.put(NEXT_ID_DN_1_19, nextINSPECTION);
		System.err.println(litener.getTableName(DN_1_19) + " is loaded.");
		closeDatabase(null, null, pstmt, resultSet);
	}

	private void closeDatabase(ConnectionManager connMgr, Connection conn, PreparedStatement pstmt, ResultSet resultSet) {
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

	public IMap<Integer, CNC> getIdMapCNC01() {
		return idMapCNC01;
	}

	public void setIdMapCNC01(IMap<Integer, CNC> idMapCNC01) {
		this.idMapCNC01 = idMapCNC01;
	}

	public MultiMap<String, CNC> getLocationMMapCNC01() {
		return locationMMapCNC01;
	}

	public void setLocationMMapCNC01(MultiMap<String, CNC> locationMMapCNC01) {
		this.locationMMapCNC01 = locationMMapCNC01;
	}

	public MultiMap<Integer, CNC> getProNoMMapCNC01() {
		return proNoMMapCNC01;
	}

	public void setProNoMMapCNC01(MultiMap<Integer, CNC> proNoMMapCNC01) {
		this.proNoMMapCNC01 = proNoMMapCNC01;
	}

	public MultiMap<Integer, CNC> getSeqNoMMapCNC01() {
		return seqNoMMapCNC01;
	}

	public void setSeqNoMMapCNC01(MultiMap<Integer, CNC> seqNoMMapCNC01) {
		this.seqNoMMapCNC01 = seqNoMMapCNC01;
	}

	public IMap<Integer, CNC> getIdMapCNC02() {
		return idMapCNC02;
	}

	public void setIdMapCNC02(IMap<Integer, CNC> idMapCNC02) {
		this.idMapCNC02 = idMapCNC02;
	}

	public MultiMap<String, CNC> getLocationMMapCNC02() {
		return locationMMapCNC02;
	}

	public void setLocationMMapCNC02(MultiMap<String, CNC> locationMMapCNC02) {
		this.locationMMapCNC02 = locationMMapCNC02;
	}

	public MultiMap<Integer, CNC> getProNoMMapCNC02() {
		return proNoMMapCNC02;
	}

	public void setProNoMMapCNC02(MultiMap<Integer, CNC> proNoMMapCNC02) {
		this.proNoMMapCNC02 = proNoMMapCNC02;
	}

	public MultiMap<Integer, CNC> getSeqNoMMapCNC02() {
		return seqNoMMapCNC02;
	}

	public void setSeqNoMMapCNC02(MultiMap<Integer, CNC> seqNoMMapCNC02) {
		this.seqNoMMapCNC02 = seqNoMMapCNC02;
	}

	public IMap<Integer, CNC> getIdMapCNC03() {
		return idMapCNC03;
	}

	public void setIdMapCNC03(IMap<Integer, CNC> idMapCNC03) {
		this.idMapCNC03 = idMapCNC03;
	}

	public MultiMap<String, CNC> getLocationMMapCNC03() {
		return locationMMapCNC03;
	}

	public void setLocationMMapCNC03(MultiMap<String, CNC> locationMMapCNC03) {
		this.locationMMapCNC03 = locationMMapCNC03;
	}

	public MultiMap<Integer, CNC> getProNoMMapCNC03() {
		return proNoMMapCNC03;
	}

	public void setProNoMMapCNC03(MultiMap<Integer, CNC> proNoMMapCNC03) {
		this.proNoMMapCNC03 = proNoMMapCNC03;
	}

	public MultiMap<Integer, CNC> getSeqNoMMapCNC03() {
		return seqNoMMapCNC03;
	}

	public void setSeqNoMMapCNC03(MultiMap<Integer, CNC> seqNoMMapCNC03) {
		this.seqNoMMapCNC03 = seqNoMMapCNC03;
	}

	public IMap<Integer, CNC> getIdMapCNC04() {
		return idMapCNC04;
	}

	public void setIdMapCNC04(IMap<Integer, CNC> idMapCNC04) {
		this.idMapCNC04 = idMapCNC04;
	}

	public MultiMap<String, CNC> getLocationMMapCNC04() {
		return locationMMapCNC04;
	}

	public void setLocationMMapCNC04(MultiMap<String, CNC> locationMMapCNC04) {
		this.locationMMapCNC04 = locationMMapCNC04;
	}

	public MultiMap<Integer, CNC> getProNoMMapCNC04() {
		return proNoMMapCNC04;
	}

	public void setProNoMMapCNC04(MultiMap<Integer, CNC> proNoMMapCNC04) {
		this.proNoMMapCNC04 = proNoMMapCNC04;
	}

	public MultiMap<Integer, CNC> getSeqNoMMapCNC04() {
		return seqNoMMapCNC04;
	}

	public void setSeqNoMMapCNC04(MultiMap<Integer, CNC> seqNoMMapCNC04) {
		this.seqNoMMapCNC04 = seqNoMMapCNC04;
	}

	public IMap<Integer, CNC> getIdMapCNC05() {
		return idMapCNC05;
	}

	public void setIdMapCNC05(IMap<Integer, CNC> idMapCNC05) {
		this.idMapCNC05 = idMapCNC05;
	}

	public MultiMap<String, CNC> getLocationMMapCNC05() {
		return locationMMapCNC05;
	}

	public void setLocationMMapCNC05(MultiMap<String, CNC> locationMMapCNC05) {
		this.locationMMapCNC05 = locationMMapCNC05;
	}

	public MultiMap<Integer, CNC> getProNoMMapCNC05() {
		return proNoMMapCNC05;
	}

	public void setProNoMMapCNC05(MultiMap<Integer, CNC> proNoMMapCNC05) {
		this.proNoMMapCNC05 = proNoMMapCNC05;
	}

	public MultiMap<Integer, CNC> getSeqNoMMapCNC05() {
		return seqNoMMapCNC05;
	}

	public void setSeqNoMMapCNC05(MultiMap<Integer, CNC> seqNoMMapCNC05) {
		this.seqNoMMapCNC05 = seqNoMMapCNC05;
	}

	public IMap<Integer, CNC> getIdMapCNC06() {
		return idMapCNC06;
	}

	public void setIdMapCNC06(IMap<Integer, CNC> idMapCNC06) {
		this.idMapCNC06 = idMapCNC06;
	}

	public MultiMap<String, CNC> getLocationMMapCNC06() {
		return locationMMapCNC06;
	}

	public void setLocationMMapCNC06(MultiMap<String, CNC> locationMMapCNC06) {
		this.locationMMapCNC06 = locationMMapCNC06;
	}

	public MultiMap<Integer, CNC> getProNoMMapCNC06() {
		return proNoMMapCNC06;
	}

	public void setProNoMMapCNC06(MultiMap<Integer, CNC> proNoMMapCNC06) {
		this.proNoMMapCNC06 = proNoMMapCNC06;
	}

	public MultiMap<Integer, CNC> getSeqNoMMapCNC06() {
		return seqNoMMapCNC06;
	}

	public void setSeqNoMMapCNC06(MultiMap<Integer, CNC> seqNoMMapCNC06) {
		this.seqNoMMapCNC06 = seqNoMMapCNC06;
	}

	public IMap<Integer, CNC> getIdMapCNC07() {
		return idMapCNC07;
	}

	public void setIdMapCNC07(IMap<Integer, CNC> idMapCNC07) {
		this.idMapCNC07 = idMapCNC07;
	}

	public MultiMap<String, CNC> getLocationMMapCNC07() {
		return locationMMapCNC07;
	}

	public void setLocationMMapCNC07(MultiMap<String, CNC> locationMMapCNC07) {
		this.locationMMapCNC07 = locationMMapCNC07;
	}

	public MultiMap<Integer, CNC> getProNoMMapCNC07() {
		return proNoMMapCNC07;
	}

	public void setProNoMMapCNC07(MultiMap<Integer, CNC> proNoMMapCNC07) {
		this.proNoMMapCNC07 = proNoMMapCNC07;
	}

	public MultiMap<Integer, CNC> getSeqNoMMapCNC07() {
		return seqNoMMapCNC07;
	}

	public void setSeqNoMMapCNC07(MultiMap<Integer, CNC> seqNoMMapCNC07) {
		this.seqNoMMapCNC07 = seqNoMMapCNC07;
	}

	public IMap<Integer, CNC> getIdMapCNC08() {
		return idMapCNC08;
	}

	public void setIdMapCNC08(IMap<Integer, CNC> idMapCNC08) {
		this.idMapCNC08 = idMapCNC08;
	}

	public MultiMap<String, CNC> getLocationMMapCNC08() {
		return locationMMapCNC08;
	}

	public void setLocationMMapCNC08(MultiMap<String, CNC> locationMMapCNC08) {
		this.locationMMapCNC08 = locationMMapCNC08;
	}

	public MultiMap<Integer, CNC> getProNoMMapCNC08() {
		return proNoMMapCNC08;
	}

	public void setProNoMMapCNC08(MultiMap<Integer, CNC> proNoMMapCNC08) {
		this.proNoMMapCNC08 = proNoMMapCNC08;
	}

	public MultiMap<Integer, CNC> getSeqNoMMapCNC08() {
		return seqNoMMapCNC08;
	}

	public void setSeqNoMMapCNC08(MultiMap<Integer, CNC> seqNoMMapCNC08) {
		this.seqNoMMapCNC08 = seqNoMMapCNC08;
	}

	public IMap<Integer, HEAT> getIdMapHEAT() {
		return idMapHEAT;
	}

	public void setIdMapHEAT(IMap<Integer, HEAT> idMapHEAT) {
		this.idMapHEAT = idMapHEAT;
	}

	public MultiMap<Double, HEAT> getVoltMMapHEAT() {
		return voltMMapHEAT;
	}

	public void setVoltMMapHEAT(MultiMap<Double, HEAT> voltMMapHEAT) {
		this.voltMMapHEAT = voltMMapHEAT;
	}

	public MultiMap<Double, HEAT> getAmpereMMapHEAT() {
		return ampereMMapHEAT;
	}

	public void setAmpereMMapHEAT(MultiMap<Double, HEAT> ampereMMapHEAT) {
		this.ampereMMapHEAT = ampereMMapHEAT;
	}

	public MultiMap<Double, HEAT> geteOutputMMapHEAT() {
		return eOutputMMapHEAT;
	}

	public void seteOutputMMapHEAT(MultiMap<Double, HEAT> eOutputMMapHEAT) {
		this.eOutputMMapHEAT = eOutputMMapHEAT;
	}

	public MultiMap<Integer, HEAT> getAmountMMapHEAT() {
		return amountMMapHEAT;
	}

	public void setAmountMMapHEAT(MultiMap<Integer, HEAT> amountMMapHEAT) {
		this.amountMMapHEAT = amountMMapHEAT;
	}

	public MultiMap<Double, HEAT> getTemperatureMMapHEAT() {
		return temperatureMMapHEAT;
	}

	public void setTemperatureMMapHEAT(MultiMap<Double, HEAT> temperatureMMapHEAT) {
		this.temperatureMMapHEAT = temperatureMMapHEAT;
	}

	public MultiMap<Double, HEAT> getFlowMMapHEAT() {
		return flowMMapHEAT;
	}

	public void setFlowMMapHEAT(MultiMap<Double, HEAT> flowMMapHEAT) {
		this.flowMMapHEAT = flowMMapHEAT;
	}

	public MultiMap<Integer, HEAT> getRpmMMapHEAT() {
		return rpmMMapHEAT;
	}

	public void setRpmMMapHEAT(MultiMap<Integer, HEAT> rpmMMapHEAT) {
		this.rpmMMapHEAT = rpmMMapHEAT;
	}

	public MultiMap<Integer, HEAT> getWorkTimeMMapHEAT() {
		return workTimeMMapHEAT;
	}

	public void setWorkTimeMMapHEAT(MultiMap<Integer, HEAT> workTimeMMapHEAT) {
		this.workTimeMMapHEAT = workTimeMMapHEAT;
	}

	public IMap<Integer, RACK> getIdMapRACK01() {
		return idMapRACK01;
	}

	public void setIdMapRACK01(IMap<Integer, RACK> idMapRACK01) {
		this.idMapRACK01 = idMapRACK01;
	}

	public MultiMap<Integer, RACK> getDiseMMapRACK01() {
		return diseMMapRACK01;
	}

	public void setDiseMMapRACK01(MultiMap<Integer, RACK> diseMMapRACK01) {
		this.diseMMapRACK01 = diseMMapRACK01;
	}

	public MultiMap<Integer, RACK> getCountMMapRACK01() {
		return countMMapRACK01;
	}

	public void setCountMMapRACK01(MultiMap<Integer, RACK> countMMapRACK01) {
		this.countMMapRACK01 = countMMapRACK01;
	}

	public MultiMap<Double, RACK> getTemperatureMMapRACK01() {
		return temperatureMMapRACK01;
	}

	public void setTemperatureMMapRACK01(
			MultiMap<Double, RACK> temperatureMMapRACK01) {
		this.temperatureMMapRACK01 = temperatureMMapRACK01;
	}

	public MultiMap<Integer, RACK> getCountSumMMapRACK01() {
		return countSumMMapRACK01;
	}

	public void setCountSumMMapRACK01(MultiMap<Integer, RACK> countSumMMapRACK01) {
		this.countSumMMapRACK01 = countSumMMapRACK01;
	}

	public IMap<Integer, RACK> getIdMapRACK02() {
		return idMapRACK02;
	}

	public void setIdMapRACK02(IMap<Integer, RACK> idMapRACK02) {
		this.idMapRACK02 = idMapRACK02;
	}

	public MultiMap<Integer, RACK> getDiseMMapRACK02() {
		return diseMMapRACK02;
	}

	public void setDiseMMapRACK02(MultiMap<Integer, RACK> diseMMapRACK02) {
		this.diseMMapRACK02 = diseMMapRACK02;
	}

	public MultiMap<Integer, RACK> getCountMMapRACK02() {
		return countMMapRACK02;
	}

	public void setCountMMapRACK02(MultiMap<Integer, RACK> countMMapRACK02) {
		this.countMMapRACK02 = countMMapRACK02;
	}

	public MultiMap<Double, RACK> getTemperatureMMapRACK02() {
		return temperatureMMapRACK02;
	}

	public void setTemperatureMMapRACK02(
			MultiMap<Double, RACK> temperatureMMapRACK02) {
		this.temperatureMMapRACK02 = temperatureMMapRACK02;
	}

	public MultiMap<Integer, RACK> getCountSumMMapRACK02() {
		return countSumMMapRACK02;
	}

	public void setCountSumMMapRACK02(MultiMap<Integer, RACK> countSumMMapRACK02) {
		this.countSumMMapRACK02 = countSumMMapRACK02;
	}

	public IMap<Integer, RACK> getIdMapRACK03() {
		return idMapRACK03;
	}

	public void setIdMapRACK03(IMap<Integer, RACK> idMapRACK03) {
		this.idMapRACK03 = idMapRACK03;
	}

	public MultiMap<Integer, RACK> getDiseMMapRACK03() {
		return diseMMapRACK03;
	}

	public void setDiseMMapRACK03(MultiMap<Integer, RACK> diseMMapRACK03) {
		this.diseMMapRACK03 = diseMMapRACK03;
	}

	public MultiMap<Integer, RACK> getCountMMapRACK03() {
		return countMMapRACK03;
	}

	public void setCountMMapRACK03(MultiMap<Integer, RACK> countMMapRACK03) {
		this.countMMapRACK03 = countMMapRACK03;
	}

	public MultiMap<Double, RACK> getTemperatureMMapRACK03() {
		return temperatureMMapRACK03;
	}

	public void setTemperatureMMapRACK03(
			MultiMap<Double, RACK> temperatureMMapRACK03) {
		this.temperatureMMapRACK03 = temperatureMMapRACK03;
	}

	public MultiMap<Integer, RACK> getCountSumMMapRACK03() {
		return countSumMMapRACK03;
	}

	public void setCountSumMMapRACK03(MultiMap<Integer, RACK> countSumMMapRACK03) {
		this.countSumMMapRACK03 = countSumMMapRACK03;
	}

	public IMap<Integer, CLEAN> getIdMapCLEAN() {
		return idMapCLEAN;
	}

	public void setIdMapCLEAN(IMap<Integer, CLEAN> idMapCLEAN) {
		this.idMapCLEAN = idMapCLEAN;
	}

	public MultiMap<Double, CLEAN> getOutput1MMapCLEAN() {
		return output1MMapCLEAN;
	}

	public void setOutput1MMapCLEAN(MultiMap<Double, CLEAN> output1mMapCLEAN) {
		output1MMapCLEAN = output1mMapCLEAN;
	}

	public MultiMap<Double, CLEAN> getOutAngleMMapCLEAN() {
		return outAngleMMapCLEAN;
	}

	public void setOutAngleMMapCLEAN(MultiMap<Double, CLEAN> outAngleMMapCLEAN) {
		this.outAngleMMapCLEAN = outAngleMMapCLEAN;
	}

	public MultiMap<Double, CLEAN> getCorrectAngleMMapCLEAN() {
		return correctAngleMMapCLEAN;
	}

	public void setCorrectAngleMMapCLEAN(MultiMap<Double, CLEAN> correctAngleMMapCLEAN) {
		this.correctAngleMMapCLEAN = correctAngleMMapCLEAN;
	}

	public MultiMap<Integer, CLEAN> getCorrectPartMMapCLEAN() {
		return correctPartMMapCLEAN;
	}

	public void setCorrectPartMMapCLEAN(
			MultiMap<Integer, CLEAN> correctPartMMapCLEAN) {
		this.correctPartMMapCLEAN = correctPartMMapCLEAN;
	}

	public MultiMap<Integer, CLEAN> getDataCountMMapCLEAN() {
		return dataCountMMapCLEAN;
	}

	public void setDataCountMMapCLEAN(MultiMap<Integer, CLEAN> dataCountMMapCLEAN) {
		this.dataCountMMapCLEAN = dataCountMMapCLEAN;
	}

	public MultiMap<Double, CLEAN> getOutput2MMapCLEAN() {
		return output2MMapCLEAN;
	}

	public void setOutput2MMapCLEAN(MultiMap<Double, CLEAN> output2mMapCLEAN) {
		output2MMapCLEAN = output2mMapCLEAN;
	}

	public MultiMap<Double, CLEAN> getResult1MMapCLEAN() {
		return result1MMapCLEAN;
	}

	public void setResult1MMapCLEAN(MultiMap<Double, CLEAN> result1mMapCLEAN) {
		result1MMapCLEAN = result1mMapCLEAN;
	}

	public MultiMap<Double, CLEAN> getResult2MMapCLEAN() {
		return result2MMapCLEAN;
	}

	public void setResult2MMapCLEAN(MultiMap<Double, CLEAN> result2mMapCLEAN) {
		result2MMapCLEAN = result2mMapCLEAN;
	}

	public MultiMap<Double, CLEAN> getResult3MMapCLEAN() {
		return result3MMapCLEAN;
	}

	public void setResult3MMapCLEAN(MultiMap<Double, CLEAN> result3mMapCLEAN) {
		result3MMapCLEAN = result3mMapCLEAN;
	}

	public MultiMap<Double, CLEAN> getCorrectionMMapCLEAN() {
		return correctionMMapCLEAN;
	}

	public void setCorrectionMMapCLEAN(MultiMap<Double, CLEAN> correctionMMapCLEAN) {
		this.correctionMMapCLEAN = correctionMMapCLEAN;
	}

	public MultiMap<Integer, CLEAN> getFairAmountMMapCLEAN() {
		return fairAmountMMapCLEAN;
	}

	public void setFairAmountMMapCLEAN(MultiMap<Integer, CLEAN> fairAmountMMapCLEAN) {
		this.fairAmountMMapCLEAN = fairAmountMMapCLEAN;
	}

	public MultiMap<Integer, CLEAN> getFaultAmountMMapCLEAN() {
		return faultAmountMMapCLEAN;
	}

	public void setFaultAmountMMapCLEAN(
			MultiMap<Integer, CLEAN> faultAmountMMapCLEAN) {
		this.faultAmountMMapCLEAN = faultAmountMMapCLEAN;
	}

	public IMap<Integer, POLISH> getIdMapPOLISH() {
		return idMapPOLISH;
	}

	public void setIdMapPOLISH(IMap<Integer, POLISH> idMapPOLISH) {
		this.idMapPOLISH = idMapPOLISH;
	}

	public MultiMap<Double, POLISH> getWholeLengthMMapPOLISH() {
		return wholeLengthMMapPOLISH;
	}

	public void setWholeLengthMMapPOLISH(
			MultiMap<Double, POLISH> wholeLengthMMapPOLISH) {
		this.wholeLengthMMapPOLISH = wholeLengthMMapPOLISH;
	}

	public MultiMap<Double, POLISH> getShakingMMapPOLISH() {
		return shakingMMapPOLISH;
	}

	public void setShakingMMapPOLISH(MultiMap<Double, POLISH> shakingMMapPOLISH) {
		this.shakingMMapPOLISH = shakingMMapPOLISH;
	}

	public IMap<Integer, INSPECTION> getIdMapINSPECTION() {
		return idMapINSPECTION;
	}

	public void setIdMapINSPECTION(IMap<Integer, INSPECTION> idMapINSPECTION) {
		this.idMapINSPECTION = idMapINSPECTION;
	}

	public MultiMap<String, INSPECTION> getTimeMMapINSPECTION() {
		return timeMMapINSPECTION;
	}

	public void setTimeMMapINSPECTION(
			MultiMap<String, INSPECTION> timeMMapINSPECTION) {
		this.timeMMapINSPECTION = timeMMapINSPECTION;
	}

	public MultiMap<String, INSPECTION> getTypeMMapINSPECTION() {
		return typeMMapINSPECTION;
	}

	public void setTypeMMapINSPECTION(
			MultiMap<String, INSPECTION> typeMMapINSPECTION) {
		this.typeMMapINSPECTION = typeMMapINSPECTION;
	}

	public MultiMap<String, INSPECTION> getResultMMapINSPECTION() {
		return resultMMapINSPECTION;
	}

	public void setResultMMapINSPECTION(
			MultiMap<String, INSPECTION> resultMMapINSPECTION) {
		this.resultMMapINSPECTION = resultMMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getExDiameter1MMapINSPECTION() {
		return exDiameter1MMapINSPECTION;
	}

	public void setExDiameter1MMapINSPECTION(
			MultiMap<Double, INSPECTION> exDiameter1MMapINSPECTION) {
		this.exDiameter1MMapINSPECTION = exDiameter1MMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getExDiameter2MMapINSPECTION() {
		return exDiameter2MMapINSPECTION;
	}

	public void setExDiameter2MMapINSPECTION(
			MultiMap<Double, INSPECTION> exDiameter2MMapINSPECTION) {
		this.exDiameter2MMapINSPECTION = exDiameter2MMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getExDiameter3MMapINSPECTION() {
		return exDiameter3MMapINSPECTION;
	}

	public void setExDiameter3MMapINSPECTION(
			MultiMap<Double, INSPECTION> exDiameter3MMapINSPECTION) {
		this.exDiameter3MMapINSPECTION = exDiameter3MMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getWholeMMapINSPECTION() {
		return wholeMMapINSPECTION;
	}

	public void setWholeMMapINSPECTION(
			MultiMap<Double, INSPECTION> wholeMMapINSPECTION) {
		this.wholeMMapINSPECTION = wholeMMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getHeightMMapINSPECTION() {
		return heightMMapINSPECTION;
	}

	public void setHeightMMapINSPECTION(
			MultiMap<Double, INSPECTION> heightMMapINSPECTION) {
		this.heightMMapINSPECTION = heightMMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getSplitPointMMapINSPECTION() {
		return splitPointMMapINSPECTION;
	}

	public void setSplitPointMMapINSPECTION(
			MultiMap<Double, INSPECTION> splitPointMMapINSPECTION) {
		this.splitPointMMapINSPECTION = splitPointMMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getRoundness1MMapINSPECTION() {
		return roundness1MMapINSPECTION;
	}

	public void setRoundness1MMapINSPECTION(
			MultiMap<Double, INSPECTION> roundness1mMapINSPECTION) {
		roundness1MMapINSPECTION = roundness1mMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getStakingMMapINSPECTION() {
		return stakingMMapINSPECTION;
	}

	public void setStakingMMapINSPECTION(
			MultiMap<Double, INSPECTION> stakingMMapINSPECTION) {
		this.stakingMMapINSPECTION = stakingMMapINSPECTION;
	}

	public MultiMap<Double, INSPECTION> getRoundness2MMapINSPECTION() {
		return roundness2MMapINSPECTION;
	}

	public void setRoundness2MMapINSPECTION(
			MultiMap<Double, INSPECTION> roundness2mMapINSPECTION) {
		roundness2MMapINSPECTION = roundness2mMapINSPECTION;
	}
}