package com.example.movie_data.Dao;

import com.example.movie_data.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDaoRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPasswordAndUserPermission(String email, String password,String userPermission);
}
