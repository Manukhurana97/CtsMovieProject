package com.example.movie_data.Response;

public class serverResponse {

    private String status;
    private String message;
    private String usertype;
    private String Auth_TOKEN;
    private Object object;


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

    public String getAuth_TOKEN() {
        return Auth_TOKEN;
    }

    public void setAuth_TOKEN(String auth_TOKEN) {
        Auth_TOKEN = auth_TOKEN;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
