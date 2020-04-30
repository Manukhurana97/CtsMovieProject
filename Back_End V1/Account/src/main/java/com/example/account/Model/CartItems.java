package com.example.account.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class CartItems implements Serializable {

    private static final long serialVersionUID = -2455760938054036364L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;
    private int amount;
//    private int mo_id;
//    private String productname;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cartid", nullable = false)
    @JsonIgnore
    private Cart cart;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "movieid" )
    private Movie movie;




}
