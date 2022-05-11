package com.patchwork.app.utils;

public enum ConsoleColor {
    RED("\033[1;31m"),
    GREEN("\033[1;32m"),
    BLUE("\033[1;34m"),
    WHITE("\033[1;37m");

    private final String code;

    ConsoleColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
