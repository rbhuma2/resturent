package com.core.exception.application;

public class JobParametersException extends RuntimeException {

    private static final long serialVersionUID = -1107576467954102543L;

    private Object[] args = new Object[] {};

    public JobParametersException() {
        super();
    }

    public JobParametersException(String message) {
        super(message);
    }

    public JobParametersException(String message, Object[] args) {
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
