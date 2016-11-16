package com.anthonykim.smartfactory.imdg.table;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class INSPECTION implements DataSerializable {
    private int id;
    private String time;
    private String type;
    private String result;
    private double exDiameter1;
    private double exDiameter2;
    private double exDiameter3;
    private double whole;
    private double height;
    private double splitPoint;
    private double roundness1;
    private double staking;
    private double roundness2;
    private boolean isSync;

    public static boolean NOT_SYNCHED = false;
    public static boolean SYNCHED = true;

    public INSPECTION() {
    }

    public INSPECTION(int id, String time, String type, String result,
                      double exDiameter1, double exDiameter2, double exDiameter3,
                      double whole, double height, double splitPoint,
                      double roundness1, double staking, double roundness2, boolean isSync) {
        this.id = id;
        this.time = time;
        this.type = type;
        this.result = result;
        this.exDiameter1 = exDiameter1;
        this.exDiameter2 = exDiameter2;
        this.exDiameter3 = exDiameter3;
        this.whole = whole;
        this.height = height;
        this.splitPoint = splitPoint;
        this.roundness1 = roundness1;
        this.staking = staking;
        this.roundness2 = roundness2;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getExDiameter1() {
        return exDiameter1;
    }

    public void setExDiameter1(double exDiameter1) {
        this.exDiameter1 = exDiameter1;
    }

    public double getExDiameter2() {
        return exDiameter2;
    }

    public void setExDiameter2(double exDiameter2) {
        this.exDiameter2 = exDiameter2;
    }

    public double getExDiameter3() {
        return exDiameter3;
    }

    public void setExDiameter3(double exDiameter3) {
        this.exDiameter3 = exDiameter3;
    }

    public double getWhole() {
        return whole;
    }

    public void setWhole(double whole) {
        this.whole = whole;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getSplitPoint() {
        return splitPoint;
    }

    public void setSplitPoint(double splitPoint) {
        this.splitPoint = splitPoint;
    }

    public double getRoundness1() {
        return roundness1;
    }

    public void setRoundness1(double roundness1) {
        this.roundness1 = roundness1;
    }

    public double getStaking() {
        return staking;
    }

    public void setStaking(double staking) {
        this.staking = staking;
    }

    public double getRoundness2() {
        return roundness2;
    }

    public void setRoundness2(double roundness2) {
        this.roundness2 = roundness2;
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readInt();
        time = in.readUTF();
        type = in.readUTF();
        result = in.readUTF();
        exDiameter1 = in.readDouble();
        exDiameter2 = in.readDouble();
        exDiameter3 = in.readDouble();
        whole = in.readDouble();
        height = in.readDouble();
        splitPoint = in.readDouble();
        roundness1 = in.readDouble();
        staking = in.readDouble();
        roundness2 = in.readDouble();
        isSync = in.readBoolean();
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(time);
        out.writeUTF(type);
        out.writeUTF(result);
        out.writeDouble(exDiameter1);
        out.writeDouble(exDiameter2);
        out.writeDouble(exDiameter3);
        out.writeDouble(whole);
        out.writeDouble(height);
        out.writeDouble(splitPoint);
        out.writeDouble(roundness1);
        out.writeDouble(staking);
        out.writeDouble(roundness2);
        out.writeBoolean(isSync);
    }
}