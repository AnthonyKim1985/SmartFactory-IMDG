package com.anthonykim.smartfactory.imdg.table;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class CNC implements DataSerializable {
    private int id;
    private String location;
    private int proNo;
    private int seqNo;
    private boolean isSync;

    public static boolean NOT_SYNCHED = false;
    public static boolean SYNCHED = true;

    public CNC() {
    }

    public CNC(int id, String location, int proNo, int seqNo, boolean isSync) {
        this.id = id;
        this.location = location;
        this.proNo = proNo;
        this.seqNo = seqNo;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getProNo() {
        return proNo;
    }

    public void setProNo(int proNo) {
        this.proNo = proNo;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public String toString() {
        return new String("id:" + id + " location:" + location + " proNo:" + proNo + " seqNo:" + seqNo + "\n");
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readInt();
        location = in.readUTF();
        proNo = in.readInt();
        seqNo = in.readInt();
        isSync = in.readBoolean();
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(location);
        out.writeInt(proNo);
        out.writeInt(seqNo);
        out.writeBoolean(isSync);
    }
}
