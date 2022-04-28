package com.patchwork.app.backend;

public class Player {

    public String name;
    public QuiltBoard quiltBoard;
    public int nrButtons;

    public Player(String name) {
        this.name = name;
        this.quiltBoard = new QuiltBoard();
        this.nrButtons = 5;
    }
}
