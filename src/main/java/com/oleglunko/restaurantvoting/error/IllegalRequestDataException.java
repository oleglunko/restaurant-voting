package com.oleglunko.restaurantvoting.error;

public class IllegalRequestDataException extends RuntimeException {

    public IllegalRequestDataException(String message) {
        super(message);
    }
}
