package com.patchwork.app.backend.Inputs;

/*
Stub just to get GameController functioning
 */

import com.patchwork.app.backend.GameInputObserver;
import com.patchwork.app.backend.Move;

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
