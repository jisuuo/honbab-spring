package com.example.honbabspring.common.exception;

import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException {
    private final String field;

    public DuplicateException(String field, String message) {
        super(message);
        this.field = field;
    }

}
