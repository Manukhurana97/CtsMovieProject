package com.example.movie_data.Response;

import com.example.movie_data.Model.Address;
import com.example.movie_data.Model.User;

import java.io.Serializable;

public class userResponse implements Serializable {

    private static final long serialVersionUID = 4744643015194204171L;
    private String Status;
    private String message;
    private String Auth_token;
    private User user;
    private Address address;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuth_token() {
        return Auth_token;
    }

    public void setAuth_token(String auth_token) {
        Auth_token = auth_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
