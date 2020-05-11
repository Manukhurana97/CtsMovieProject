package com.example.account.Response;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 4744643015194204171L;

    private int Status;
    private String message;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
