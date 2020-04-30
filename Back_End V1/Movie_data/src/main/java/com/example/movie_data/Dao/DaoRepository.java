package com.example.movie_data.Dao;

import com.example.movie_data.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DaoRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findMovieByNameAndId(String name, int id);
    List<Movie> findMovieByName(String name);

}
