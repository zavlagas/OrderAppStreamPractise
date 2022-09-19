package com.speedorder.orderapp.exception.custom;

public class DateTimeFormatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DateTimeFormatException(String message) {
        super(message);
    }
}
