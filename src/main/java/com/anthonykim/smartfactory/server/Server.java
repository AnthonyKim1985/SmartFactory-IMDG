package com.anthonykim.smartfactory.server;

import com.anthonykim.smartfactory.imdg.hazelcast.SmartFactoryIMDG;
import com.anthonykim.smartfactory.imdg.table.CNC;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements Runnable {
    @Override
    public void run() {
        ClientConfig clientConfig = new ClientConfig();
        HazelcastInstance hzInstance = HazelcastClient.newHazelcastClient(clientConfig);

        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(20);

        HashMap<String, Integer> machineMap = new HashMap<String, Integer>();
        machineMap.put("CNC01", SmartFactoryIMDG.DN_1_01);
        machineMap.put("CNC02", SmartFactoryIMDG.DN_1_02);
        machineMap.put("CNC03", SmartFactoryIMDG.DN_1_03);
        machineMap.put("CNC04", SmartFactoryIMDG.DN_1_04);
        machineMap.put("CNC05", SmartFactoryIMDG.DN_1_05);
        machineMap.put("CNC06", SmartFactoryIMDG.DN_1_06);
        machineMap.put("CNC07", SmartFactoryIMDG.DN_1_07);
        machineMap.put("CNC08", SmartFactoryIMDG.DN_1_08);
        machineMap.put("HEAT", SmartFactoryIMDG.DN_1_09);
        machineMap.put("RACK01", SmartFactoryIMDG.DN_1_11);
        machineMap.put("RACK02", SmartFactoryIMDG.DN_1_12);
        machineMap.put("RACK03", SmartFactoryIMDG.DN_1_13);
        machineMap.put("CLEAN", SmartFactoryIMDG.DN_1_14);
        machineMap.put("POLISH", SmartFactoryIMDG.DN_1_15);
        machineMap.put("INSPECTION", SmartFactoryIMDG.DN_1_19);

        Runnable serverTask = new Runnable() {
            @SuppressWarnings("resource")
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8000);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        clientProcessingPool.submit(new ClientTask(clientSocket, hzInstance, machineMap));
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;
        private HashMap<String, Integer> machineMap;
        private HazelcastInstance hzInstance;

        private ClientTask(Socket clientSocket, HazelcastInstance hzInstance, HashMap<String, Integer> machineMap) {
            this.clientSocket = clientSocket;
            this.machineMap = machineMap;
            this.hzInstance = hzInstance;
        }

        @Override
        public void run() {
            String json = new String("{");
            try {
                // 클라이언트로 부터 Query를 받는다.
                InputStream in = clientSocket.getInputStream();
                DataInputStream dis = new DataInputStream(in);
                String query = dis.readUTF();

                IMap<Integer, CNC> map = hzInstance.getMap("idMapCNC01");
                Set<Integer> keySet = map.keySet();
                Iterator<Integer> keys = keySet.iterator();
                while (keys.hasNext()) {
                    Integer key = keys.next();
                    CNC cnc = map.get(key);
                    if (cnc.getId() < 10)
                        json += "'id':'" + cnc.getId() + "','location':'" + cnc.getLocation() + "','proNo':'" + cnc.getProNo() + "','seqNo':'" + cnc.getSeqNo() + "'\n";
                }
                json += "}";

                OutputStream out = clientSocket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);
                dos.writeUTF(json);

                System.err.println(getTime() + ":Server response to "
                        + clientSocket.getInetAddress().getHostAddress() + ", " + "query:" + query);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String getTime() {
            SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
            return f.format(new Date());
        }
    }
}