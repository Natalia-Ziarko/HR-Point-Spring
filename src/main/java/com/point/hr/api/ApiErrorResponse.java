package com.point.hr.api;

import lombok.Data;

@Data // INFO: Lombok generates getters, setters, toString, equals, and hashCode method
public class ApiErrorResponse {

    private int status;
    private String message;
    private long timeStamp;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
