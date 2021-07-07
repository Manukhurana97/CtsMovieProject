package com.example.account.Dao;

import com.example.account.Model.CartItems;
import com.example.account.Model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserOrderRepositry extends JpaRepository<UserOrder, Integer> {

    public List<UserOrder> findByUser_UseridAndDate(int id, Date date);
}
