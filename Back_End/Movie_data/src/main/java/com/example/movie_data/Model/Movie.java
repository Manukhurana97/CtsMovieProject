package com.example.movie_data.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieid;
    private String name;
    private String description;
    private float rating;
    private String director;
    private String language;
    private int price;
    private byte[] picBite;


}
