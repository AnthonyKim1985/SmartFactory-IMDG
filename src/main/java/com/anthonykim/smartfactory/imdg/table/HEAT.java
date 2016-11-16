package com.anthonykim.smartfactory.imdg.table;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class HEAT implements DataSerializable {
    private int id;
    private double volt;
    private double ampere;
    private double eOutput;
    private int amount;
    private double temperature;
    private double flow;
    private int rpm;
    private int workTime;
    private boolean isSync;

    public static boolean NOT_SYNCHED = false;
    public static boolean SYNCHED = true;

    public HEAT() {
    }

    public HEAT(int id, double volt,
                double ampere, double eOutput, int amount, double temperature,
                double flow, int rpm, int workTime, boolean isSync) {
        this.id = id;
        this.volt = volt;
        this.ampere = ampere;
        this.eOutput = eOutput;
        this.amount = amount;
        this.temperature = temperature;
        this.flow = flow;
        this.rpm = rpm;
        this.workTime = workTime;
        this.isSync = isSync;
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readInt();
        volt = in.readDouble();
        ampere = in.readDouble();
        eOutput = in.readDouble();
        amount = in.readInt();
        temperature = in.readDouble();
        flow = in.readDouble();
        rpm = in.readInt();
        workTime = in.readInt();
        isSync = in.readBoolean();
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(id);
        out.writeDouble(volt);
        out.writeDouble(ampere);
        out.writeDouble(eOutput);
        out.writeInt(amount);
        out.writeDouble(temperature);
        out.writeDouble(flow);
        out.writeInt(rpm);
        out.writeInt(workTime);
        out.writeBoolean(isSync);
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

    public double getVolt() {
        return volt;
    }

    public void setVolt(double volt) {
        this.volt = volt;
    }

    public double getAmpere() {
        return ampere;
    }

    public void setAmpere(double ampere) {
        this.ampere = ampere;
    }

    public double geteOutput() {
        return eOutput;
    }

    public void seteOutput(double eOutput) {
        this.eOutput = eOutput;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFlow() {
        return flow;
    }

    public void setFlow(double flow) {
        this.flow = flow;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }
}
