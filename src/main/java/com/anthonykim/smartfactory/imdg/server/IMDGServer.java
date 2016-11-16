package com.anthonykim.smartfactory.imdg.server;


import com.anthonykim.smartfactory.imdg.hazelcast.SmartFactoryIMDG;
import com.anthonykim.smartfactory.server.Server;

public class IMDGServer {
    private static SmartFactoryIMDG smartFactoryIMDG = new SmartFactoryIMDG("SensorDB");

    public static void main(String[] args) {
        smartFactoryIMDG.initIMDG();
        smartFactoryIMDG.fetchDatabase();

        Thread syncherThread = new Thread(new DBSyncher());
        syncherThread.start();

        Thread serverThread = new Thread(new Server());
        serverThread.start();
    }
}