package com.linkedin.linkedin.exception;

import java.time.LocalDateTime;

public class ApiError {

    private LocalDateTime timeStamp;
    private String message;
    private int status;

    public ApiError(String message, int status) {
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
