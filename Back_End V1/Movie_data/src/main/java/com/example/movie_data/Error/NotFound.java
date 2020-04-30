package com.example.movie_data.Error;

@SuppressWarnings("serial")
public class NotFound extends RuntimeException {
    public String mgs;

    public NotFound(String mgs) {
        super();
        this.mgs = mgs;
    }


    public String DisplayMgs()
    {
        return mgs;
    }
}
