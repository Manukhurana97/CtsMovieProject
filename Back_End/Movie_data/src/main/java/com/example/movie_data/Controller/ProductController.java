package com.example.movie_data.Controller;

import com.example.movie_data.Model.Movie;
import com.example.movie_data.Service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductController {

    @Autowired
    ServiceImpl service;

    Movie Mo;

    @PostMapping("/Add")
    public Movie create(@RequestBody Movie movie) {

        return service.create(movie);
    }

    @GetMapping("")
    public List<Movie> find_all() {
        return service.findAll();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Cacheable(value = "Moviedata", key = "'Moviedata'+#id")
    public Optional<Movie> find_By_id(@PathVariable int id) {
        return service.find_by_id(id);
    }


    @GetMapping("Search={name}")
    public List<Movie> find_By_Name(@PathVariable(value = "name") String name) {
        return service.find_by_Name(name);
    }


    @GetMapping("/{Name}/{id}")
    public List<Movie> find_By_Nameandid(@PathVariable(value = "Name") String Name, @PathVariable(value = "id") int id) {
        return service.find_by_Nameandid(Name, id);
    }


    @PutMapping("/Update")
    public Movie Update(@RequestBody Movie movie) {
        return service.update(movie);
    }

    @DeleteMapping("/Delete/{id}")
    public void Delete_by_id(@PathVariable(value = "id") int id) {
        service.delete(id);
    }
}
