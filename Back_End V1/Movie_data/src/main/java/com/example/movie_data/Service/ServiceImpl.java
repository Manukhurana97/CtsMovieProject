package com.example.movie_data.Service;

import com.example.movie_data.Dao.DaoRepository;
import com.example.movie_data.Model.Movie;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
   public DaoRepository dao;

    @Override
    public Movie create(Movie movie) {
        return dao.saveAndFlush(movie);
    }

    @Override
    public List<Movie> findAll() {
        return dao.findAll();
    }


    @Override
    public Optional<Movie> find_by_id(int id) {
        return dao.findById(id);
    }

    @Override
    public List<Movie> find_by_Name(String Name) {
        return dao.findMovieByName(Name);
    }


    @Override
    public List<Movie> find_by_Nameandid(String Name, int id) {
        return dao.findMovieByNameAndId(Name, id);
    }

    @Override
    public Movie update(Movie movie) {
        return dao.saveAndFlush(movie);
    }

    @Override
    public void delete(int id) {
        dao.deleteById(id);
    }
}
