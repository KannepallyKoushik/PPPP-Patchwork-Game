package com.patchwork.app.peer_tests.adapters;

import com.patchwork.app.backend.GameFactory;
import com.patchwork.app.backend.TimeBoard;

public class AdaptedTimeBoard extends TimeBoard {
    public AdaptedTimeBoard(int size) {
        super(GameFactory.createPlayers(), size);
    }

    public void advanceTimeToken(TimeToken t1, TimeToken t2) {
        int move = (t2.getPosition() - t1.getPosition()) + 1;
        int newPosition = Math.min(t1.getPosition() + move, this.spaces.size() - 1);
        t1.setPosition(newPosition);
    }
}
