package com.example.account.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private float rating;
    private String director;
    private String language;
    private int price;
    private byte[] picBite;



//        @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "movie")
//    private List<CartItems> cartItems ;

}
