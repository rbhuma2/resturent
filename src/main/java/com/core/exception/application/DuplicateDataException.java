package com.core.exception.application;

public class DuplicateDataException extends RuntimeException {

    private static final long serialVersionUID = -1107576467954102543L;

    private Object[] args = new Object[] {};

    public DuplicateDataException() {
        super();
    }

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(String message, Object[] args) {
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
