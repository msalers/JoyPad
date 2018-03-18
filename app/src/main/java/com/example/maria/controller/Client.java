package com.example.maria.controller;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by maria on 18/03/2018.
 */

public class Client extends AsyncTask<Void, Void,Void> {

    String response = "";
    Socket socket;

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            socket = new Socket("192.168.1.65",5656);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

