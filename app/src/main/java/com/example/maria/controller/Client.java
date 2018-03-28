package com.example.maria.controller;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by maria on 18/03/2018.
 */

public class Client extends AsyncTask<String, String,String> {

    boolean up, down, right, left;
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    String name;
    byte health, points;
    double FPS =60;
MainActivity main;


    public Client(String name, MainActivity main) {
        this.name = name;
        this.main = main;
    }


    @Override
    protected String doInBackground(String... params) {
        try{
            socket = new Socket("192.168.1.65",5656);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            out.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
        }


        double delta = 0;
        long now, lastTime = System.nanoTime();

        while(true){

                now = System.nanoTime();
                delta += (now-lastTime) * FPS / 10e8;
                lastTime = now;
                if (delta >= 1) {
                   // Log.d("A","A");
                    delta--;
                    try {
                        Packet packet = new Packet(up, down, left, right);
                        out.write(packet.getByte());

                        Packet.HPPacket hppack = new Packet.HPPacket(in.readByte());
                        hppack.getHealth();
                        int point = hppack.getPoints()*100;

                        this.publishProgress("Vite:"+hppack.getHealth()+" Punti:"+ point);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        TextView display = (TextView) main.findViewById(R.id.HPTxt);
        display.setText(values[0]);
    }


    public void close() throws IOException {
        socket.close();
        in.close();
        out.close();
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public byte getHealth(){
        return health;
    }

    public byte getPoints(){
        return points;
    }
}

