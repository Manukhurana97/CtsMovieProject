package com.example.movie_data.Error;

public class ErrorMgs {


    public String Mgs;
    public Long time;
    public int status;


    public ErrorMgs() {
        super();
    }

    public ErrorMgs(String mgs, Long time, int status) {
        Mgs = mgs;
        this.time = time;
        this.status = status;
    }

    public String getMgs() {
        return Mgs;
    }

    public void setMgs(String mgs) {
        Mgs = mgs;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
