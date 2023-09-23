package com.credibanco.Test.exceptions;

import static com.credibanco.Test.util.Constant.ERROR_SAVE;

public class DataBaseException extends RuntimeException {

    public DataBaseException(String details) {
        super(details);
    }
}