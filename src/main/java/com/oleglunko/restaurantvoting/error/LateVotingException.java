package com.oleglunko.restaurantvoting.error;

public class LateVotingException extends RuntimeException {

    public LateVotingException(String message) {
        super(message);
    }
}