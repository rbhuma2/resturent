package com.core.exception.application;

public class AppAuthenticationException extends RuntimeException {
    private static final long serialVersionUID = -3845574518872003019L;

    private Object[] args = new Object[] {};

    public AppAuthenticationException() {
        super();
    }

    public AppAuthenticationException(String message) {
        super(message);
    }

    public AppAuthenticationException(String message, Object[] args) {
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