package com.anthonykim.smartfactory.generator;

import com.smartfactory.imdg.hazelcast.SmartFactoryIMDG;

public class RACKDriver {
	public static void main(String[] args) {
		Thread [] tRACKs = new Thread[3];
		
		int machineNo =  SmartFactoryIMDG.DN_1_11;
		for (int tIdx=0; tIdx<tRACKs.length; tIdx++, machineNo <<= 0x01) {
			tRACKs[tIdx] = new Thread(new RACKSimulator(machineNo, RACKSimulator.WRITE_IMDG));
			tRACKs[tIdx].start();
		}
	}	
}
