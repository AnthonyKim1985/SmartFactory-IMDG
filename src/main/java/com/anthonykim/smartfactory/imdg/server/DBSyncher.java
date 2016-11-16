package com.anthonykim.smartfactory.imdg.server;

import com.anthonykim.smartfactory.imdg.db.ConnectionManager;
import com.anthonykim.smartfactory.imdg.db.MySQLConnectionManager;
import com.anthonykim.smartfactory.imdg.hazelcast.SmartFactoryIMDG;
import com.anthonykim.smartfactory.imdg.table.*;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class DBSyncher implements Runnable {
    public DBSyncher() {
    }

    @Override
    public synchronized void run() {
        try {
            ClientConfig clientConfig = new ClientConfig();
            HazelcastInstance hzInstance = HazelcastClient.newHazelcastClient(clientConfig);

            ConnectionManager connMgr = new MySQLConnectionManager();
            Connection conn = connMgr.getConnection();

            while (true) {
                sleep(1000);
                for (int machineNo = 0x00000001; machineNo <= 0x00004000; machineNo <<= 0x00000001) {
                    switch (machineNo) {
                        case SmartFactoryIMDG.DN_1_01:
                            synchDN01(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_02:
                            synchDN02(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_03:
                            synchDN03(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_04:
                            synchDN04(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_05:
                            synchDN05(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_06:
                            synchDN06(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_07:
                            synchDN07(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_08:
                            synchDN08(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_09:
                            synchDN09(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_11:
                            synchDN11(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_12:
                            synchDN12(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_13:
                            synchDN13(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_14:
                            synchDN14(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_15:
                            synchDN15(hzInstance, conn);
                            break;
                        case SmartFactoryIMDG.DN_1_19:
                            synchDN19(hzInstance, conn);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void synchDN01(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_01(id, location, proNo, seqNo) values(?,?,?,?);";

        IMap<Integer, CNC> idMapCNC = hzInstance.getMap("idMapCNC01");
        MultiMap<String, CNC> locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC01");
        MultiMap<Integer, CNC> proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC01");
        MultiMap<Integer, CNC> seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC01");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_01);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CNC columnCNC = (CNC) iterator.next();
            int id = columnCNC.getId();
            String location = columnCNC.getLocation();
            int proNo = columnCNC.getProNo();
            int seqNo = columnCNC.getSeqNo();

            idMapCNC.put(id, columnCNC);
            locationMMapCNC.put(location, columnCNC);
            proNoMMapCNC.put(proNo, columnCNC);
            seqNoMMapCNC.put(seqNo, columnCNC);

            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setInt(3, proNo);
            pstmt.setInt(4, seqNo);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_01, columnCNC);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN02(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_02(id, location, proNo, seqNo) values(?,?,?,?);";

        IMap<Integer, CNC> idMapCNC = hzInstance.getMap("idMapCNC02");
        MultiMap<String, CNC> locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC02");
        MultiMap<Integer, CNC> proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC02");
        MultiMap<Integer, CNC> seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC02");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_02);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CNC columnCNC = (CNC) iterator.next();
            int id = columnCNC.getId();
            String location = columnCNC.getLocation();
            int proNo = columnCNC.getProNo();
            int seqNo = columnCNC.getSeqNo();

            idMapCNC.put(id, columnCNC);
            locationMMapCNC.put(location, columnCNC);
            proNoMMapCNC.put(proNo, columnCNC);
            seqNoMMapCNC.put(seqNo, columnCNC);

            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setInt(3, proNo);
            pstmt.setInt(4, seqNo);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_02, columnCNC);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN03(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_03(id, location, proNo, seqNo) values(?,?,?,?);";

        IMap<Integer, CNC> idMapCNC = hzInstance.getMap("idMapCNC03");
        MultiMap<String, CNC> locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC03");
        MultiMap<Integer, CNC> proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC03");
        MultiMap<Integer, CNC> seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC03");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_03);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CNC columnCNC = (CNC) iterator.next();
            int id = columnCNC.getId();
            String location = columnCNC.getLocation();
            int proNo = columnCNC.getProNo();
            int seqNo = columnCNC.getSeqNo();

            idMapCNC.put(id, columnCNC);
            locationMMapCNC.put(location, columnCNC);
            proNoMMapCNC.put(proNo, columnCNC);
            seqNoMMapCNC.put(seqNo, columnCNC);

            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setInt(3, proNo);
            pstmt.setInt(4, seqNo);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_03, columnCNC);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN04(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_04(id, location, proNo, seqNo) values(?,?,?,?);";

        IMap<Integer, CNC> idMapCNC = hzInstance.getMap("idMapCNC04");
        MultiMap<String, CNC> locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC04");
        MultiMap<Integer, CNC> proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC04");
        MultiMap<Integer, CNC> seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC04");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_04);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CNC columnCNC = (CNC) iterator.next();
            int id = columnCNC.getId();
            String location = columnCNC.getLocation();
            int proNo = columnCNC.getProNo();
            int seqNo = columnCNC.getSeqNo();

            idMapCNC.put(id, columnCNC);
            locationMMapCNC.put(location, columnCNC);
            proNoMMapCNC.put(proNo, columnCNC);
            seqNoMMapCNC.put(seqNo, columnCNC);

            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setInt(3, proNo);
            pstmt.setInt(4, seqNo);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_04, columnCNC);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN05(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_05(id, location, proNo, seqNo) values(?,?,?,?);";

        IMap<Integer, CNC> idMapCNC = hzInstance.getMap("idMapCNC05");
        MultiMap<String, CNC> locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC05");
        MultiMap<Integer, CNC> proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC05");
        MultiMap<Integer, CNC> seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC05");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_05);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CNC columnCNC = (CNC) iterator.next();
            int id = columnCNC.getId();
            String location = columnCNC.getLocation();
            int proNo = columnCNC.getProNo();
            int seqNo = columnCNC.getSeqNo();

            idMapCNC.put(id, columnCNC);
            locationMMapCNC.put(location, columnCNC);
            proNoMMapCNC.put(proNo, columnCNC);
            seqNoMMapCNC.put(seqNo, columnCNC);

            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setInt(3, proNo);
            pstmt.setInt(4, seqNo);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_05, columnCNC);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN06(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_06(id, location, proNo, seqNo) values(?,?,?,?);";

        IMap<Integer, CNC> idMapCNC = hzInstance.getMap("idMapCNC06");
        MultiMap<String, CNC> locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC06");
        MultiMap<Integer, CNC> proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC06");
        MultiMap<Integer, CNC> seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC06");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_06);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CNC columnCNC = (CNC) iterator.next();
            int id = columnCNC.getId();
            String location = columnCNC.getLocation();
            int proNo = columnCNC.getProNo();
            int seqNo = columnCNC.getSeqNo();

            idMapCNC.put(id, columnCNC);
            locationMMapCNC.put(location, columnCNC);
            proNoMMapCNC.put(proNo, columnCNC);
            seqNoMMapCNC.put(seqNo, columnCNC);

            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setInt(3, proNo);
            pstmt.setInt(4, seqNo);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_06, columnCNC);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN07(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_07(id, location, proNo, seqNo) values(?,?,?,?);";

        IMap<Integer, CNC> idMapCNC = hzInstance.getMap("idMapCNC07");
        MultiMap<String, CNC> locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC07");
        MultiMap<Integer, CNC> proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC07");
        MultiMap<Integer, CNC> seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC07");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_07);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CNC columnCNC = (CNC) iterator.next();
            int id = columnCNC.getId();
            String location = columnCNC.getLocation();
            int proNo = columnCNC.getProNo();
            int seqNo = columnCNC.getSeqNo();

            idMapCNC.put(id, columnCNC);
            locationMMapCNC.put(location, columnCNC);
            proNoMMapCNC.put(proNo, columnCNC);
            seqNoMMapCNC.put(seqNo, columnCNC);

            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setInt(3, proNo);
            pstmt.setInt(4, seqNo);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_07, columnCNC);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN08(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_08(id, location, proNo, seqNo) values(?,?,?,?);";

        IMap<Integer, CNC> idMapCNC = hzInstance.getMap("idMapCNC08");
        MultiMap<String, CNC> locationMMapCNC = hzInstance.getMultiMap("locationMMapCNC08");
        MultiMap<Integer, CNC> proNoMMapCNC = hzInstance.getMultiMap("proNoMMapCNC08");
        MultiMap<Integer, CNC> seqNoMMapCNC = hzInstance.getMultiMap("seqNoMMapCNC08");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_08);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CNC columnCNC = (CNC) iterator.next();
            int id = columnCNC.getId();
            String location = columnCNC.getLocation();
            int proNo = columnCNC.getProNo();
            int seqNo = columnCNC.getSeqNo();

            idMapCNC.put(id, columnCNC);
            locationMMapCNC.put(location, columnCNC);
            proNoMMapCNC.put(proNo, columnCNC);
            seqNoMMapCNC.put(seqNo, columnCNC);

            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setInt(3, proNo);
            pstmt.setInt(4, seqNo);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_08, columnCNC);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN09(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_09(id, volt, ampere, eOutput, amount, temperature, flow, rpm, worktime) values(?,?,?,?,?,?,?,?,?);";

        IMap<Integer, HEAT> idMapHEAT = hzInstance.getMap("idMapHEAT");
        MultiMap<Double, HEAT> voltMMapHEAT = hzInstance.getMultiMap("voltMMapHEAT");
        MultiMap<Double, HEAT> ampereMMapHEAT = hzInstance.getMultiMap("ampereMMapHEAT");
        MultiMap<Double, HEAT> eOutputMMapHEAT = hzInstance.getMultiMap("eOutputMMapHEAT");
        MultiMap<Integer, HEAT> amountMMapHEAT = hzInstance.getMultiMap("amountMMapHEAT");
        MultiMap<Double, HEAT> temperatureMMapHEAT = hzInstance.getMultiMap("temperatureMMapHEAT");
        MultiMap<Double, HEAT> flowMMapHEAT = hzInstance.getMultiMap("flowMMapHEAT");
        MultiMap<Integer, HEAT> rpmMMapHEAT = hzInstance.getMultiMap("rpmMMapHEAT");
        MultiMap<Integer, HEAT> workTimeMMapHEAT = hzInstance.getMultiMap("workTimeMMapHEAT");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_09);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            HEAT columnHEAT = (HEAT) iterator.next();
            int id = columnHEAT.getId();
            double volt = columnHEAT.getVolt();
            double ampere = columnHEAT.getAmpere();
            double eOutput = columnHEAT.geteOutput();
            int amount = columnHEAT.getAmount();
            double temperature = columnHEAT.getTemperature();
            double flow = columnHEAT.getFlow();
            int rpm = columnHEAT.getRpm();
            int workTime = columnHEAT.getWorkTime();

            idMapHEAT.put(id, columnHEAT);
            voltMMapHEAT.put(volt, columnHEAT);
            ampereMMapHEAT.put(ampere, columnHEAT);
            eOutputMMapHEAT.put(eOutput, columnHEAT);
            amountMMapHEAT.put(amount, columnHEAT);
            temperatureMMapHEAT.put(temperature, columnHEAT);
            flowMMapHEAT.put(flow, columnHEAT);
            rpmMMapHEAT.put(rpm, columnHEAT);
            workTimeMMapHEAT.put(workTime, columnHEAT);

            pstmt.setInt(1, id);
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
            updatedTarget.remove(SmartFactoryIMDG.DN_1_09, columnHEAT);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN11(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_11(id, dise, count, temperature, countSum) values(?,?,?,?,?);";

        IMap<Integer, RACK> idMapRACK = hzInstance.getMap("idMapRACK01");
        MultiMap<Integer, RACK> diseMMapRACK = hzInstance.getMultiMap("diseMMapRACK01");
        MultiMap<Integer, RACK> countMMapRACK = hzInstance.getMultiMap("countMMapRACK01");
        MultiMap<Double, RACK> temperatureMMapRACK = hzInstance.getMultiMap("temperatureMMapRACK01");
        MultiMap<Integer, RACK> countSumMMapRACK = hzInstance.getMultiMap("countSumMMapRACK01");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_11);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            RACK columnRACK = (RACK) iterator.next();
            int id = columnRACK.getId();
            int dise = columnRACK.getDise();
            int count = columnRACK.getCount();
            double temperature = columnRACK.getTemperature();
            int countSum = columnRACK.getCountSum();

            idMapRACK.put(id, columnRACK);
            diseMMapRACK.put(dise, columnRACK);
            countMMapRACK.put(count, columnRACK);
            temperatureMMapRACK.put(temperature, columnRACK);
            countSumMMapRACK.put(countSum, columnRACK);

            pstmt.setInt(1, id);
            pstmt.setInt(2, dise);
            pstmt.setInt(3, count);
            pstmt.setDouble(4, temperature);
            pstmt.setInt(5, countSum);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_11, columnRACK);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN12(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_12(id, dise, count, temperature, countSum) values(?,?,?,?,?);";

        IMap<Integer, RACK> idMapRACK = hzInstance.getMap("idMapRACK02");
        MultiMap<Integer, RACK> diseMMapRACK = hzInstance.getMultiMap("diseMMapRACK02");
        MultiMap<Integer, RACK> countMMapRACK = hzInstance.getMultiMap("countMMapRACK02");
        MultiMap<Double, RACK> temperatureMMapRACK = hzInstance.getMultiMap("temperatureMMapRACK02");
        MultiMap<Integer, RACK> countSumMMapRACK = hzInstance.getMultiMap("countSumMMapRACK02");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_12);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            RACK columnRACK = (RACK) iterator.next();
            int id = columnRACK.getId();
            int dise = columnRACK.getDise();
            int count = columnRACK.getCount();
            double temperature = columnRACK.getTemperature();
            int countSum = columnRACK.getCountSum();

            idMapRACK.put(id, columnRACK);
            diseMMapRACK.put(dise, columnRACK);
            countMMapRACK.put(count, columnRACK);
            temperatureMMapRACK.put(temperature, columnRACK);
            countSumMMapRACK.put(countSum, columnRACK);

            pstmt.setInt(1, id);
            pstmt.setInt(2, dise);
            pstmt.setInt(3, count);
            pstmt.setDouble(4, temperature);
            pstmt.setInt(5, countSum);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_12, columnRACK);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN13(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_13(id, dise, count, temperature, countSum) values(?,?,?,?,?);";

        IMap<Integer, RACK> idMapRACK = hzInstance.getMap("idMapRACK03");
        MultiMap<Integer, RACK> diseMMapRACK = hzInstance.getMultiMap("diseMMapRACK03");
        MultiMap<Integer, RACK> countMMapRACK = hzInstance.getMultiMap("countMMapRACK03");
        MultiMap<Double, RACK> temperatureMMapRACK = hzInstance.getMultiMap("temperatureMMapRACK03");
        MultiMap<Integer, RACK> countSumMMapRACK = hzInstance.getMultiMap("countSumMMapRACK03");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_13);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            RACK columnRACK = (RACK) iterator.next();
            int id = columnRACK.getId();
            int dise = columnRACK.getDise();
            int count = columnRACK.getCount();
            double temperature = columnRACK.getTemperature();
            int countSum = columnRACK.getCountSum();

            idMapRACK.put(id, columnRACK);
            diseMMapRACK.put(dise, columnRACK);
            countMMapRACK.put(count, columnRACK);
            temperatureMMapRACK.put(temperature, columnRACK);
            countSumMMapRACK.put(countSum, columnRACK);

            pstmt.setInt(1, id);
            pstmt.setInt(2, dise);
            pstmt.setInt(3, count);
            pstmt.setDouble(4, temperature);
            pstmt.setInt(5, countSum);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_13, columnRACK);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN14(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_14(id, output1, outAngle, correctAngle, "
                + "correctPart, dataCount, output2, result1, result2, result3, correction, fairAmount, faultAmount) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?);";

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

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_14);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            CLEAN columnCLEAN = (CLEAN) iterator.next();
            int id = columnCLEAN.getId();
            double output1 = columnCLEAN.getOutput1();
            double outAngle = columnCLEAN.getOutAngle();
            double correctAngle = columnCLEAN.getCorrectAngle();
            int correctPart = columnCLEAN.getCorrectPart();
            int dataCount = columnCLEAN.getDataCount();
            double output2 = columnCLEAN.getOutput2();
            double result1 = columnCLEAN.getResult1();
            double result2 = columnCLEAN.getResult2();
            double result3 = columnCLEAN.getResult3();
            double correction = columnCLEAN.getCorrection();
            int fairAmount = columnCLEAN.getFairAmount();
            int faultAmount = columnCLEAN.getFaultAmount();

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

            pstmt.setInt(1, id);
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
            updatedTarget.remove(SmartFactoryIMDG.DN_1_14, columnCLEAN);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN15(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_15(id, wholeLength, shaking) values(?,?,?);";

        IMap<Integer, POLISH> idMapPOLISH = hzInstance.getMap("idMapPOLISH");
        MultiMap<Double, POLISH> wholeLengthMMapPOLISH = hzInstance.getMultiMap("wholeLengthMMapPOLISH");
        MultiMap<Double, POLISH> shakingMMapPOLISH = hzInstance.getMultiMap("shakingMMapPOLISH");

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_15);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            POLISH columnPOLISH = (POLISH) iterator.next();
            int id = columnPOLISH.getId();
            double wholeLength = columnPOLISH.getWholeLength();
            double shaking = columnPOLISH.getShaking();

            idMapPOLISH.put(id, columnPOLISH);
            wholeLengthMMapPOLISH.put(wholeLength, columnPOLISH);
            shakingMMapPOLISH.put(shaking, columnPOLISH);

            pstmt.setInt(1, id);
            pstmt.setDouble(2, wholeLength);
            pstmt.setDouble(3, shaking);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            updatedTarget.remove(SmartFactoryIMDG.DN_1_15, columnPOLISH);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private synchronized void synchDN19(HazelcastInstance hzInstance, Connection conn) throws SQLException {
        final String query = "insert into SensorDB.DN_1_19(id, time, type, result, exDiameter1, exDiameter2, exDiameter3, "
                + "whole, height, splitPoint, roundness1, staking, roundness2) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";

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

        MultiMap<Integer, Object> updatedTarget = hzInstance.getMultiMap(SmartFactoryIMDG.UPDATE_TARGET);
        Collection<Object> table = updatedTarget.get(SmartFactoryIMDG.DN_1_19);
        Iterator<Object> iterator = table.iterator();
        PreparedStatement pstmt = conn.prepareStatement(query);

        while (iterator.hasNext()) {
            INSPECTION columnINSPECTION = (INSPECTION) iterator.next();

            int id = columnINSPECTION.getId();
            String time = columnINSPECTION.getTime();
            String type = columnINSPECTION.getType();
            String result = columnINSPECTION.getResult();
            double exDiameter1 = columnINSPECTION.getExDiameter1();
            double exDiameter2 = columnINSPECTION.getExDiameter2();
            double exDiameter3 = columnINSPECTION.getExDiameter3();
            double whole = columnINSPECTION.getWhole();
            double height = columnINSPECTION.getHeight();
            double splitPoint = columnINSPECTION.getSplitPoint();
            double roundness1 = columnINSPECTION.getRoundness1();
            double staking = columnINSPECTION.getStaking();
            double roundness2 = columnINSPECTION.getRoundness2();

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

            pstmt.setInt(1, id);
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
            updatedTarget.remove(SmartFactoryIMDG.DN_1_19, columnINSPECTION);
        }
        closeDatabase(null, null, pstmt, null);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
}
