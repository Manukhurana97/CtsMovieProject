package com.example.account.Model;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

@Entity
@Data
public class OrdersItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderid;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Userorderid", nullable = false)
    private UserOrder userOrder;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Movie> movie;



}
