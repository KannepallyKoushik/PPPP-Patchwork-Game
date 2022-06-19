package com.patchwork.app.backend.exceptions;

/**
 * Generic exception raised when an invalid or unexpected call is made on the Game class.
 */
public class GameException extends Exception {

    public GameException(String message) {
        super(message);
    }
}
