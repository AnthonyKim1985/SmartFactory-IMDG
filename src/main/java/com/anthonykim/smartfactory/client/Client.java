package com.anthonykim.smartfactory.client;


import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by hyuk0 on 2016-11-16.
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8000);

            long start = System.nanoTime();
            // Query로 데이터를 요청한다.
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            //dos.writeUTF("Select * from SensorDB.DN_1_11 where id < 10;");
            dos.writeUTF("insert into ");

            // 요청한 Query의 결과를 JSON으로 받는다.
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            String json = dis.readUTF();
            long end = System.nanoTime();

            System.out.println(end - start + "ns");

            System.out.println(json);
            in.close();
            out.close();
            socket.close();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}