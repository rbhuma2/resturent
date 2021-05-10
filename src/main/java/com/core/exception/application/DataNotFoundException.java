package com.core.exception.application;


public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3845574518872003019L;

    private Object[] args = new Object[] {};

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}