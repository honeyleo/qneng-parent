package com.manager.common.exception;

public class AdminException extends Exception {

    private static final long serialVersionUID=1L;

    public AdminException() {

    }

    public AdminException(String message) {
        super(message);
    }

    public AdminException(Throwable cause) {
        super(cause);
    }

    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }

}
