package com.patchwork.app.backend.Inputs;

import com.patchwork.app.backend.Move;

public class MockGameInput extends GameInput {

    private boolean running;
    private Move move = Move.WAITING;


    public void updateMove(Move move) {
        this.move = move;
        running = false;
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (move == null) {
                    Thread.sleep(10);
                }
                notify(move);
                move = null;
            }
        } catch (InterruptedException e) {
        }
    }
}
