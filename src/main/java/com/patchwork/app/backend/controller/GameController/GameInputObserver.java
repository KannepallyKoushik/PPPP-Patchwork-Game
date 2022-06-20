package com.patchwork.app.backend.controller.GameController;

import com.patchwork.app.backend.controller.GameStates.Move;

public interface GameInputObserver {


    void update(Move move);

}
