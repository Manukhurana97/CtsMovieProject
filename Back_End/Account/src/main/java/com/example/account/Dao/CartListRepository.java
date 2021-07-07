package com.example.account.Dao;

import com.example.account.Model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Optional;

@Repository
@EnableTransactionManagement
public interface CartListRepository extends JpaRepository<CartItems, Integer> {

    public List<CartItems> findAllByCart_Cartid(int id);

    public List<CartItems> findByMovie_MovieidAndCart_Cartid(int productid, int movie_movieid);

    public void deleteAllByCart_Cartid(int cart_cartid);

    public void deleteCartItemsByCart_CartidAndMovie_Movieid(int cartid, int movieid);


}
