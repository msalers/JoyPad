package com.example.maria.controller;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by maria on 18/03/2018.
 */

public class Client extends AsyncTask<Void, Void,Void> {

    boolean up, down, right, left;
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    String name;
    byte health, points;
    MainActivity.ClientHandler handler;
    double FPS =60;



    public Client(String name, MainActivity.ClientHandler handler) {
        this.name = name;
        this.handler= handler;
    }

    private void sendHealth(byte health){
        handler.sendMessage(Message.obtain(handler, MainActivity.ClientHandler.UPDATE_HEALTH, health));
    }

    private void sendPoints(byte pounts){
        handler.sendMessage(Message.obtain(handler, MainActivity.ClientHandler.UPDATE_POINTS, points));
    }


    @Override
    protected Void doInBackground(Void... voids) {
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

                        health = in.readByte();
                        //in.readByte();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


        }

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

