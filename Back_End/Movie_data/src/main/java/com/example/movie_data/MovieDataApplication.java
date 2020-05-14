package com.example.movie_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class MovieDataApplication {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }


    public static void main(String[] args) {
        SpringApplication.run(MovieDataApplication.class, args);
    }

}
