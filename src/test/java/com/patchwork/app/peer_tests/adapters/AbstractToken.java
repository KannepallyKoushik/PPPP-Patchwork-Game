package com.patchwork.app.peer_tests.adapters;

public abstract class AbstractToken {
    private int position = 0;

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
