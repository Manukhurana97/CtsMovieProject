package com.example.account.Dao;

import com.example.account.Model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Repository
@EnableTransactionManagement
public interface CartListRepository extends JpaRepository<CartItems, Integer> {

   public List<CartItems> findAllByCart_Cartid(int id);
   public List<CartItems> findByCart_Cartid(int id);

}
