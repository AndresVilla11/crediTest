package com.credibanco.Test.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad Request ";

    public BadRequestException(String details) {
        super(DESCRIPTION + details);
    }
}

