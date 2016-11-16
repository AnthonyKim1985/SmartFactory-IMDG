package com.anthonykim.smartfactory.imdg.table;


import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class CLEAN implements DataSerializable {
    private int id;
    private double output1;
    private double outAngle;
    private double correctAngle;
    private int correctPart;
    private int dataCount;
    private double output2;
    private double result1;
    private double result2;
    private double result3;
    private double correction;
    private int fairAmount;
    private int faultAmount;
    private boolean isSync;

    public static boolean NOT_SYNCHED = false;
    public static boolean SYNCHED = true;


    public CLEAN() {
    }

    public CLEAN(int id, double output1, double outAngle,
                 double correctAngle, int correctPart, int dataCount,
                 double output2, double result1, double result2, double result3,
                 double correction, int fairAmount, int faultAmount, boolean isSync) {
        this.id = id;
        this.output1 = output1;
        this.outAngle = outAngle;
        this.correctAngle = correctAngle;
        this.correctPart = correctPart;
        this.dataCount = dataCount;
        this.output2 = output2;
        this.result1 = result1;
        this.result2 = result2;
        this.result3 = result3;
        this.correction = correction;
        this.fairAmount = fairAmount;
        this.faultAmount = faultAmount;
        this.isSync = isSync;

    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readInt();
        output1 = in.readDouble();
        outAngle = in.readDouble();
        correctAngle = in.readDouble();
        correctPart = in.readInt();
        dataCount = in.readInt();
        output2 = in.readDouble();
        result1 = in.readDouble();
        result2 = in.readDouble();
        result3 = in.readDouble();
        correction = in.readDouble();
        fairAmount = in.readInt();
        faultAmount = in.readInt();
        isSync = in.readBoolean();
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(id);
        out.writeDouble(output1);
        out.writeDouble(outAngle);
        out.writeDouble(correctAngle);
        out.writeInt(correctPart);
        out.writeInt(dataCount);
        out.writeDouble(output2);
        out.writeDouble(result1);
        out.writeDouble(result2);
        out.writeDouble(result3);
        out.writeDouble(correction);
        out.writeInt(fairAmount);
        out.writeInt(faultAmount);
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

    public double getOutput1() {
        return output1;
    }

    public void setOutput1(double output1) {
        this.output1 = output1;
    }

    public double getOutAngle() {
        return outAngle;
    }

    public void setOutAngle(double outAngle) {
        this.outAngle = outAngle;
    }

    public double getCorrectAngle() {
        return correctAngle;
    }

    public void setCorrectAngle(double correctAngle) {
        this.correctAngle = correctAngle;
    }

    public int getCorrectPart() {
        return correctPart;
    }

    public void setCorrectPart(int correctPart) {
        this.correctPart = correctPart;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public double getOutput2() {
        return output2;
    }

    public void setOutput2(double output2) {
        this.output2 = output2;
    }

    public double getResult1() {
        return result1;
    }

    public void setResult1(double result1) {
        this.result1 = result1;
    }

    public double getResult2() {
        return result2;
    }

    public void setResult2(double result2) {
        this.result2 = result2;
    }

    public double getResult3() {
        return result3;
    }

    public void setResult3(double result3) {
        this.result3 = result3;
    }

    public double getCorrection() {
        return correction;
    }

    public void setCorrection(double correction) {
        this.correction = correction;
    }

    public int getFairAmount() {
        return fairAmount;
    }

    public void setFairAmount(int fairAmount) {
        this.fairAmount = fairAmount;
    }

    public int getFaultAmount() {
        return faultAmount;
    }

    public void setFaultAmount(int faultAmount) {
        this.faultAmount = faultAmount;
    }
}
