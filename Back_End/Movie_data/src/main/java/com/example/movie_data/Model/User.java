package com.example.movie_data.Model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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
   public Address address;


}
