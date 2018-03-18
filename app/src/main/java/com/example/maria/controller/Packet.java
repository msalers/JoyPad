package com.example.maria.controller;

/**
 * Created by maria on 18/03/2018.
 */

public class Packet {

    boolean up, down , left, right;

    public Packet(boolean up, boolean down, boolean left, boolean right){
        this.up=up;
        this.left=left;
        this.right=right;
        this.down=down;
    }

    public byte getByte() {
        byte b = 0;
        if(up) b += 8;
        if(down) b += 4;
        if(left) b += 2;
        if(right) b += 1;
        return b;
    }
}
