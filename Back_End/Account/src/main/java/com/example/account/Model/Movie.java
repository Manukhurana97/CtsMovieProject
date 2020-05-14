package com.example.account.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;


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
