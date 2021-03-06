package com.example.movie_data.Controller;

import com.example.movie_data.Dao.AddressDaoRepository;
import com.example.movie_data.Dao.UserDaoRepository;
import com.example.movie_data.Model.Movie;
import com.example.movie_data.Model.User;
import com.example.movie_data.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.movie_data.Response.serverResponse;
import com.example.movie_data.Util.*;
import org.springframework.http.HttpStatus;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Account/")
public class UserController {

    public static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    public UserDaoRepository userdao;

    @Autowired
    public AddressDaoRepository addressdao;

    @Autowired
    private Util jwtutil;

    @PostMapping("/UserRegister")
    public User adduser(@RequestBody User user) {
        return userdao.saveAndFlush(user);
    }


    @PostMapping("/AdminRegister")
    public User addadmin(@Valid @RequestBody User user) {
        return userdao.save(user);
    }

    @PostMapping("/UserLogin")
    public ResponseEntity<serverResponse> verifyuser(@Valid @RequestBody Map<String, String> credential) {
        String email = "";
        String password = "";
        if (credential.containsKey("email")) {
            email = credential.get("email");
        }
        if (credential.containsKey("password")) {
            password = credential.get("password");
        }
        User loggedUser = userdao.findByEmailAndPasswordAndUserPermission(email, password, "customer");
        serverResponse response = new serverResponse();
        if (loggedUser != null) {
            String jwtToken = jwtutil.createToken(email, password, "customer");
            response.setAuth_TOKEN(jwtToken);
            response.setMessage("SUCCESS");
            response.setStatus("200");
            response.setUsertype("customer");
        } else {
            response.setStatus("500");
            response.setMessage("ERROR");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    @PostMapping("/AdminLogin")
    public ResponseEntity<serverResponse> verifyadmin(@Valid @RequestBody Map<String, String> credential) {
        String email = "";
        String password = "";
        if (credential.containsKey("email")) {
            email = credential.get("email");
        }
        if (credential.containsKey("password")) {
            password = credential.get("password");
        }
        User loggedUser = userdao.findByEmailAndPasswordAndUserPermission(email, password, "admin");
        serverResponse response = new serverResponse();
        if (loggedUser != null) {
            String jwtToken = jwtutil.createToken(email, password, "admin");
            response.setAuth_TOKEN(jwtToken);
            response.setMessage("SUCCESS");
            response.setStatus("200");
            response.setUsertype("admin");
        } else {
            response.setStatus("500");
            response.setMessage("ERROR");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("All_User")
    public List<User> find_all() {
        return userdao.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<User> find_By_id(@PathVariable int id) {
        return userdao.findById(id);
    }

    @DeleteMapping("/Delete/{id}")
    public void deleteUser(@PathVariable int id) {
        userdao.deleteById(id);
    }


}
