package com.example.honbabspring.exception;

public class DuplicateException extends RuntimeException {
    private final String field;

    public DuplicateException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
