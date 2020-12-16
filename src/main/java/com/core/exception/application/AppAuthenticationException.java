package com.core.exception.application;

/**
 * Exception to be thrown when trying to get a non existent data
 *
 * @author hgarg5
 *
 */
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