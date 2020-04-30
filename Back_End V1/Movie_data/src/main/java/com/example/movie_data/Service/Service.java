package com.example.movie_data.Service;

import com.example.movie_data.Model.Movie;

import java.util.List;
import java.util.Optional;

public interface Service {
    public Movie create(Movie movie);
    public List<Movie> findAll();
    public Optional<Movie> find_by_id(int id);
    public List<Movie> find_by_Name(String Name);
    public List<Movie> find_by_Nameandid(String Name, int id);
    public Movie update(Movie movie);
    public void delete(int id);
}
