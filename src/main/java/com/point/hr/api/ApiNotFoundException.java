package com.point.hr.api;

public class ApiNotFoundException extends RuntimeException {

    public ApiNotFoundException(String msg) {
        super(msg);
    }
}
