package com.example.account.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(value = "true")
public class Cart implements Serializable {
    private static final long serialVersionUID = 8436097833452420298L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartid;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.ALL}, mappedBy = "cart", orphanRemoval = true)
    private List<CartItems> cartItems;

}
