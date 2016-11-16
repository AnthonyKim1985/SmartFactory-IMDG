package com.anthonykim.smartfactory.imdg.table;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class POLISH implements DataSerializable {
    private int id;
    private double wholeLength;
    private double shaking;
    private boolean isSync;

    public static boolean NOT_SYNCHED = false;
    public static boolean SYNCHED = true;

    public POLISH() {
    }

    public POLISH(int id, double wholeLength, double shaking, boolean isSync) {
        this.id = id;
        this.wholeLength = wholeLength;
        this.shaking = shaking;
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

    public double getWholeLength() {
        return wholeLength;
    }

    public void setWholeLength(double wholeLength) {
        this.wholeLength = wholeLength;
    }

    public double getShaking() {
        return shaking;
    }

    public void setShaking(double shaking) {
        this.shaking = shaking;
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readInt();
        wholeLength = in.readDouble();
        shaking = in.readDouble();
        isSync = in.readBoolean();
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(id);
        out.writeDouble(wholeLength);
        out.writeDouble(shaking);
        out.writeBoolean(isSync);
    }
}
