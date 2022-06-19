package com.patchwork.app.backend.controller.Inputs;

/*
Stub just to get GameController functioning
 */

import com.patchwork.app.backend.controller.GameController.GameInputObserver;
import com.patchwork.app.backend.controller.GameStates.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class GameInput implements Runnable {

    public List<GameInputObserver> observers;

    public GameInput() {
        observers = new ArrayList<>();
    }

    public void notify(Move move) {
        for (GameInputObserver observer : observers) {
            observer.update(move);
        }
    }

    public void subscribe(GameInputObserver observer) {
        observers.add(observer);
    }



}
