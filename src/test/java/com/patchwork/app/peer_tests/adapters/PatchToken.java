package com.patchwork.app.peer_tests.adapters;

public class PatchToken {

    private int position;

    public PatchToken(){
        position = 0;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int i){
        if (i < 0){
            throw new IllegalArgumentException();
        } else {
            position = i;
        }
    }

    public void move(int i){
        if (i < 0){
            throw new IllegalArgumentException();
        } else {
            position += i;
        }
    }
}
