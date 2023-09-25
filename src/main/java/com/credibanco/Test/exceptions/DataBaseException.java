package com.credibanco.Test.exceptions;

public class DataBaseException extends RuntimeException {

    public DataBaseException(String details) {
        super(details);
    }
}