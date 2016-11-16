package com.anthonykim.smartfactory.imdg.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;
import com.smartfactory.imdg.table.CLEAN;
import com.smartfactory.imdg.table.CNC;
import com.smartfactory.imdg.table.HEAT;
import com.smartfactory.imdg.table.INSPECTION;
import com.smartfactory.imdg.table.POLISH;
import com.smartfactory.imdg.table.RACK;

public class IMDGIndexTable {
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

	public IMDGIndexTable(HazelcastInstance hzInstance) {
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
