package com.example.movie_data.Dao;

import com.example.movie_data.Model.Address;
import com.example.movie_data.Model.CartItems;
import com.example.movie_data.Model.Movie;
import com.example.movie_data.Response.userResponse;
import com.example.movie_data.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Repository
public interface CartListRepository extends JpaRepository<CartItems, Integer> {

}
