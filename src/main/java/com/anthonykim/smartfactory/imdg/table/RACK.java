package com.anthonykim.smartfactory.imdg.table;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class RACK implements DataSerializable {
    private int id;
    private int dise;
    private int count;
    private double temperature;
    private int countSum;
    private boolean isSync;

    public static boolean NOT_SYNCHED = false;
    public static boolean SYNCHED = true;

    public RACK() {
    }

    public RACK(int id, int dise, int count, double temperature, int countSum, boolean isSync) {
        this.id = id;
        this.dise = dise;
        this.count = count;
        this.temperature = temperature;
        this.countSum = countSum;
        this.isSync = isSync;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean isSync) {
        this.isSync = isSync;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDise() {
        return dise;
    }

    public void setDise(int dise) {
        this.dise = dise;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getCountSum() {
        return countSum;
    }

    public void setCountSum(int countSum) {
        this.countSum = countSum;
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readInt();
        dise = in.readInt();
        count = in.readInt();
        temperature = in.readDouble();
        countSum = in.readInt();
        isSync = in.readBoolean();
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(id);
        out.writeInt(dise);
        out.writeInt(count);
        out.writeDouble(temperature);
        out.writeInt(countSum);
        out.writeBoolean(isSync);
    }

}
