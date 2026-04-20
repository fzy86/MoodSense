package com.example.moodsense.demo.exception;

public class MoodNotFoundException extends RuntimeException {

    public MoodNotFoundException(String message) {
        super(message);
    }
}
