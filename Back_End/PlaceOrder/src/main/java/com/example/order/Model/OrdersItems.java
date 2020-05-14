package com.example.order.Model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
