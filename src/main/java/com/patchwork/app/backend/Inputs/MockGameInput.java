package com.patchwork.app.backend.Inputs;

import com.patchwork.app.backend.Move;

public class MockGameInput extends GameInput {
    public Move move = Move.WAITING;


    public void updateMove(Move move){
        this.move = move;
    }




    @Override
    public void run() {
        notify(move);
        if (move != Move.WAITING){
            System.out.println(move);
        }
        move = Move.WAITING;
    }
}
