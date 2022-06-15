package com.patchwork.app.backend;

public enum Move {
    MOVE_LEFT,
    MOVE_RIGHT,
    MOVE_UP,
    MOVE_DOWN,

    ROTATE_CLOCKWISE,
    ROTATE_COUNTERCLOCKWISE,

    MIRROR_VERTICAL,
    MIRROR_HORIZONTAL,

    HELP,

    CONFIRM;

    public boolean isLeftRight() {
        return this == MOVE_LEFT || this == MOVE_RIGHT;
    }

    public boolean isMove() {
        return this == MOVE_LEFT || this == MOVE_RIGHT || this == MOVE_UP || this == MOVE_DOWN;
    }

    public boolean isRotate() {
        return this == ROTATE_CLOCKWISE || this == ROTATE_COUNTERCLOCKWISE;
    }

    public boolean isMirror() { return this == MIRROR_HORIZONTAL || this == MIRROR_VERTICAL;}
}
