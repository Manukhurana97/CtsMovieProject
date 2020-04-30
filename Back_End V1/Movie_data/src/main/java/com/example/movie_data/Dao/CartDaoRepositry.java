package com.example.movie_data.Dao;

import com.example.movie_data.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface CartDaoRepositry extends JpaRepository<Cart, Integer> {


}
