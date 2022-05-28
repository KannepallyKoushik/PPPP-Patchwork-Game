package com.patchwork.app.backend.Inputs;

import com.patchwork.app.backend.Move;

public class MockGameInput extends GameInput {

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void doMove(Move move) {
        notify(move);
    }
}
