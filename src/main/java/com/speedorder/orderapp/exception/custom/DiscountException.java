package com.speedorder.orderapp.exception.custom;

public class DiscountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DiscountException(String message) {
        super(message);
    }
}
