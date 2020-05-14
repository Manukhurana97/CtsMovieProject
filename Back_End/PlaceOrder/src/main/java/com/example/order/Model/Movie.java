package com.example.order.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
@JsonIgnoreProperties(value = "true")
public class Movie {

    @Id
    private int movieid;
    private String name;
    private String description;
    private float rating;
    private String director;
    private String language;
    private int price;
    private byte[] picBite;

}
