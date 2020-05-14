package com.example.account.Dao;

import com.example.account.Model.OrdersItems;
import com.example.account.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrdersItems, Integer> {
    public List<OrdersItems> findAllByUserOrder_User(User userOrder_user);
}
