package com.example.account.Dao;

import com.example.account.Model.Address;
import com.example.account.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDaoRepository extends JpaRepository<Address, Long> {
    Address findByUser(User user);
}
