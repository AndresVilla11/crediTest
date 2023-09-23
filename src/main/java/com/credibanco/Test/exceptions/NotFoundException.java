package com.credibanco.Test.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "Not Found: ";

    public NotFoundException(String details) {
        super(DESCRIPTION + details);
    }
}
