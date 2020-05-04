package com.example.account.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Data
public class CartItems implements Serializable {

    private static final long serialVersionUID = -2455760938054036364L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartitemid;

    private int time;
    private int amount;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cartid", nullable = false)
    private Cart cart;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn(name = "movieid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Movie movie;



}