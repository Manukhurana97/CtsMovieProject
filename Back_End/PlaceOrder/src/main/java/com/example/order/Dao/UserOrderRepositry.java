package com.example.order.Dao;


import com.example.order.Model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;


@Repository
@EnableTransactionManagement
public interface UserOrderRepositry extends JpaRepository<UserOrder, Integer> {
    List<UserOrder> findAllByUserid(int userid);

}
