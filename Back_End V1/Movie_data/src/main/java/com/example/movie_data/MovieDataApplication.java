package com.example.movie_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class MovieDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieDataApplication.class, args);
    }

}
