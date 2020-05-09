package com.example.account;


import com.example.account.Model.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
public class MovieData {


    @Autowired
    private WebClient.Builder webClientBuilder;


    public Movie getMovie(int Productid) {
        return webClientBuilder.build()
                .get()
                .uri("http://192.168.31.241:8000/" + Productid)
                .retrieve().bodyToMono(Movie.class)
                .block();
    }


}
