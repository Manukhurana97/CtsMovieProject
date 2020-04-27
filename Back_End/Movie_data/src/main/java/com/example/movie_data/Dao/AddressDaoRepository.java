package com.example.movie_data.Dao;

import com.example.movie_data.Model.Address;
import com.example.movie_data.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDaoRepository extends JpaRepository<Address, Long> {
    Address findByUser(User user);
}
