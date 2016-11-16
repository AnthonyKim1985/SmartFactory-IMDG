package com.anthonykim.smartfactory.imdg.hazelcast;

import com.anthonykim.smartfactory.imdg.table.*;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapEvent;
import com.hazelcast.core.MultiMap;


class DBEntryListener implements EntryListener<Integer, Object> {
	private HazelcastInstance hzInstance;
	
	public DBEntryListener(HazelcastInstance hzInstance) {
		this.hzInstance = hzInstance;
	}
	
	public String getTableName(int machineNo) {
		String machineName = null;
		switch (machineNo) {
		case SmartFactoryIMDG.DN_1_01:
			machineName = new String("CNC (TAB: DN_1_01)"); break;
		case SmartFactoryIMDG.DN_1_02:
			machineName = new String("CNC (TAB: DN_1_02)"); break;
		case SmartFactoryIMDG.DN_1_03:
			machineName = new String("CNC (TAB: DN_1_03)"); break;
		case SmartFactoryIMDG.DN_1_04:
			machineName = new String("CNC (TAB: DN_1_04)"); break;
		case SmartFactoryIMDG.DN_1_05:
			machineName = new String("CNC (TAB: DN_1_05)"); break;
		case SmartFactoryIMDG.DN_1_06:
			machineName = new String("CNC (TAB: DN_1_06)"); break;
		case SmartFactoryIMDG.DN_1_07:
			machineName = new String("CNC (TAB: DN_1_07)"); break;
		case SmartFactoryIMDG.DN_1_08:
			machineName = new String("CNC (TAB: DN_1_08)"); break;
		case SmartFactoryIMDG.DN_1_09:
			machineName = new String("HEAT (TAB: DN_1_09)"); break;
		case SmartFactoryIMDG.DN_1_11:
			machineName = new String("RACK (TAB: DN_1_11)"); break;
		case SmartFactoryIMDG.DN_1_12:
			machineName = new String("RACK (TAB: DN_1_12)"); break;
		case SmartFactoryIMDG.DN_1_13:
			machineName = new String("RACK (TAB: DN_1_13)"); break;
		case SmartFactoryIMDG.DN_1_14:
			machineName = new String("CLEAN (TAB: DN_1_14)"); break;
		case SmartFactoryIMDG.DN_1_15:
			machineName = new String("POLISH (TAB: DN_1_15)"); break;
		case SmartFactoryIMDG.DN_1_19:
			machineName = new String("INSPECTION (TAB: DN_1_19)"); break;
		}
		return machineName;
	}
	@Override
	public void entryAdded(EntryEvent<Integer, Object> event) {
		switch (event.getKey()) {
		case SmartFactoryIMDG.DN_1_01:
		case SmartFactoryIMDG.DN_1_02:
		case SmartFactoryIMDG.DN_1_03:
		case SmartFactoryIMDG.DN_1_04:
		case SmartFactoryIMDG.DN_1_05:
		case SmartFactoryIMDG.DN_1_06:
		case SmartFactoryIMDG.DN_1_07:
		case SmartFactoryIMDG.DN_1_08:
			if (((CNC) event.getValue()).isSync())
				return;
			break;
		case SmartFactoryIMDG.DN_1_09:
			if (((HEAT) event.getValue()).isSync())
				return;
			break;
		case SmartFactoryIMDG.DN_1_11:
		case SmartFactoryIMDG.DN_1_12:
		case SmartFactoryIMDG.DN_1_13:
			if (((RACK) event.getValue()).isSync())
				return;
			break;
		case SmartFactoryIMDG.DN_1_14:
			if (((CLEAN) event.getValue()).isSync())
				return;
			break;
		case SmartFactoryIMDG.DN_1_15:
			if (((POLISH) event.getValue()).isSync())
				return;
			break;
		case SmartFactoryIMDG.DN_1_19:
			if (((INSPECTION) event.getValue()).isSync())
				return;
			break;
		case SmartFactoryIMDG.NEXT_ID_DN_1_01:
		case SmartFactoryIMDG.NEXT_ID_DN_1_02:
		case SmartFactoryIMDG.NEXT_ID_DN_1_03:
		case SmartFactoryIMDG.NEXT_ID_DN_1_04:
		case SmartFactoryIMDG.NEXT_ID_DN_1_05:
		case SmartFactoryIMDG.NEXT_ID_DN_1_06:
		case SmartFactoryIMDG.NEXT_ID_DN_1_07:
		case SmartFactoryIMDG.NEXT_ID_DN_1_08:
		case SmartFactoryIMDG.NEXT_ID_DN_1_09:
		case SmartFactoryIMDG.NEXT_ID_DN_1_11:
		case SmartFactoryIMDG.NEXT_ID_DN_1_12:
		case SmartFactoryIMDG.NEXT_ID_DN_1_13:
		case SmartFactoryIMDG.NEXT_ID_DN_1_14:
		case SmartFactoryIMDG.NEXT_ID_DN_1_15:
		case SmartFactoryIMDG.NEXT_ID_DN_1_19:
			return;
		}
		System.out.printf("[IMDG-ADDED] %s(0x%04x)\n", getTableName(event.getKey()), event.getKey());
		MultiMap<Integer, Object> updateTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
		updateTarget.put(event.getKey(), event.getValue());
	}
	@Override
	public void entryEvicted(EntryEvent<Integer, Object> event) {
		int machineNo = event.getKey().intValue();
		System.out.printf("[IMDG-EVICTED] %s(0x%04x)\n", getTableName(machineNo), machineNo);
	}
	@Override
	public void entryRemoved(EntryEvent<Integer, Object> event) {
		int machineNo = event.getKey().intValue();
		System.out.printf("[IMDG-REMOVED] %s(0x%04x)\n", getTableName(machineNo), machineNo);
	}
	@Override
	public void entryUpdated(EntryEvent<Integer, Object> event) {
		int machineNo = event.getKey().intValue();
		System.out.printf("[IMDG-UPDATED] %s(0x%04x)\n", getTableName(machineNo), machineNo);
	}
	@Override
	public void mapCleared(MapEvent event) {
		System.out.println("[IMDG-MAPCLEARED]:" + event);
	}
	@Override
	public void mapEvicted(MapEvent event) {
		System.out.println("[IMDG-MAPEVICTED] " + event);
	}
}