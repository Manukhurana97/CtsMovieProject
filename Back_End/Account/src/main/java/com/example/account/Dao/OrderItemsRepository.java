package com.example.account.Dao;

import com.example.account.Model.OrdersItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrdersItems, Integer> {
}
