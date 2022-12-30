package com.utils;

public class RatingErrorException extends Exception {
    RatingErrorException(String message) {
        super(message);
    }

    RatingErrorException() {
        super();
    }
}
