package com.example.order.Model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Userorderid;

    private String Status;
    private Date date;
    private Double Amount;
    private int userid;



}
