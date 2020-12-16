package com.core.exception.application;

public class InvalidDataException extends RuntimeException {

    private static final long serialVersionUID = -1107576467954102543L;

    private Object[] args = new Object[] {};

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Object[] args) {
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
