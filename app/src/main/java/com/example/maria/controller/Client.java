package com.example.maria.controller;

import android.os.AsyncTask;

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


    public Client(String name){
        this.name=name;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            socket = new Socket("192.168.1.65",5656);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try {
            out.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                Packet packet = new Packet(up, down, left, right);
                out.write(packet.getByte());

                health = in.readByte();
                points = in.readByte();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void close() {
        if(socket!=null){
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
}

