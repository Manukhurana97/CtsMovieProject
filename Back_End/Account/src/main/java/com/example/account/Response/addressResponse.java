package com.example.account.Response;

import java.io.Serializable;
import java.util.HashMap;

public class addressResponse implements Serializable {

    private static final long serialVersionUID = 1928909901056236719L;
    private String Status;
    private String Message;
    private String Auth_Token;
    private HashMap<String, String> map;
//    private List< HashMap<String, String>> map;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getAuth_Token() {
        return Auth_Token;
    }

    public void setAuth_Token(String auth_Token) {
        Auth_Token = auth_Token;
    }


    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
}
