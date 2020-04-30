package com.example.account.Dao;

import com.example.account.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface CartDaoRepositry extends JpaRepository<Cart, Integer> {


}
