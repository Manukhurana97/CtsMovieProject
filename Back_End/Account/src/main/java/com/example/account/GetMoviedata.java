package com.example.account;

import com.example.account.Model.Movie;
import com.example.account.Response.CartResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GetMoviedata {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @HystrixCommand(fallbackMethod = "getFallBackMethod")
    public Movie getdata(int Productid)
    {
        return webClientBuilder.build()
                .get()
                .uri("http://192.168.31.241:8000/" + Productid)
                .retrieve().bodyToMono(Movie.class)
                .block();
    }


}
