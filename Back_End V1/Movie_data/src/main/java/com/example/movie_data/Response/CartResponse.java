package com.example.movie_data.Response;

public class CartResponse {
    public String status;
    public String message;
    private String auth_Token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuth_Token() {
        return auth_Token;
    }

    public void setAuth_Token(String auth_Token) {
        this.auth_Token = auth_Token;
    }
}
