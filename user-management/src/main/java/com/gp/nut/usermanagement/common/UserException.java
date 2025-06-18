package com.gp.nut.usermanagement.common;

public class UserException extends RuntimeException {
    private final Errorcode errorcode;

    public UserException(Errorcode errorcode) {
        super(errorcode.getMessage());
        this.errorcode = errorcode;
    }

    public Errorcode getErrorcode() {
        return errorcode;
    }
}
