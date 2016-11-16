package com.anthonykim.smartfactory.generator;

import com.smartfactory.imdg.hazelcast.SmartFactoryIMDG;

public class CNCDriver {
	public static void main(String[] args) {
		Thread [] tCNCs = new Thread[8];
		int machineNo =  SmartFactoryIMDG.DN_1_01;
		for (int tIndex=0; tIndex<tCNCs.length; tIndex++, machineNo <<= 0x01) {
			tCNCs[tIndex] = new Thread(new CNCSimulator(machineNo, CNCSimulator.WRITE_IMDG));
			tCNCs[tIndex].start();
		}
		//(new Thread(new CNCSimulator(SmartFactoryIMDG.DN_1_01, CNCSimulator.WRITE_IMDG))).start();
	}	
}