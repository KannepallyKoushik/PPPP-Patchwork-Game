package com.patchwork.app.backend;

/**
 * Generic exception raised when an invalid or unexpected call is made on the Game class.
 */
public class GameException extends Exception {

    GameException(String message) {
        super(message);
    }
}
