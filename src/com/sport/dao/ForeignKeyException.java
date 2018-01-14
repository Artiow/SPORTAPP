package com.sport.dao;

public class ForeignKeyException extends Exception {
    public ForeignKeyException() {

    }

    public ForeignKeyException(String msg) {
        super(msg);
    }
}
