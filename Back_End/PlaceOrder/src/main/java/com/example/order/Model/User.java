package com.example.order.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;



@Data
@JsonIgnoreProperties(value = "true")
public class User implements Serializable {

    private static final long serialVersionUID = -8850740904859933967L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int userid;
    public String name;
    @Column(unique = true)
    @Email(message = "Incorrect email")
    public String email;
    public String password;
    public String userPermission;



}
