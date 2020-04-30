package com.example.movie_data.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

@Entity
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -8850740904859933967L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int userid;
    public String name;
    public String email;
    public String password;
    public String userPermission;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Cart cart;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    public List<Address> address;


}
