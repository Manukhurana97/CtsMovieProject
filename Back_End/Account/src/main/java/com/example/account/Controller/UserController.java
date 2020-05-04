package com.example.account.Controller;

import com.example.account.Dao.AddressDaoRepository;
import com.example.account.Dao.CartDaoRepositry;
import com.example.account.Dao.UserDaoRepository;
import com.example.account.Model.Cart;
import com.example.account.Model.User;
import com.example.account.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.account.Response.serverResponse;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    public static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    public UserDaoRepository userdao;

    @Autowired
    public AddressDaoRepository addressdao;

    @Autowired
    public CartDaoRepositry cartdao;

    @Autowired
    private Util jwtutil;



    @PostMapping("/UserRegister")
    public ResponseEntity<serverResponse> adduser(@RequestBody User user) {
        serverResponse resp = new serverResponse();
        try {
            User usr = userdao.save(user);
            Cart cart = new Cart();
            cart.setUser(usr);
            usr.setCart(cart);
            createcart(cart);
            resp.setUsertype(usr.getUserPermission());
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Account Created Successfully");

        }
        catch (Exception e)
        {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setMessage("Failed to create account ");

        }

        return new  ResponseEntity<serverResponse>(resp, HttpStatus.OK);
    }


    public Cart createcart(Cart cart)
    {
        Cart cat =  cartdao.saveAndFlush(cart);
        return cat;
    }


    @PostMapping("/AdminRegister")
    public ResponseEntity<serverResponse> addadmin(@Valid @RequestBody User user) {
        serverResponse resp = new serverResponse();
        try {
            User usr = userdao.saveAndFlush(user);
            Cart cart = new Cart();
            cart.setUser(usr);
            usr.setCart(cart);
            createcart(cart);
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Admin created Successfully");
            resp.setUsertype(user.getUserPermission());

        }
        catch(Exception e)
        {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setMessage("Error Creating admin");
        }

        return new ResponseEntity<serverResponse>(resp, HttpStatus.OK);
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
            response.setStatus(HttpStatus.OK.value());
            response.setUsertype("customer");
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
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
            response.setStatus(HttpStatus.OK.value());
            response.setUsertype("admin");
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid username or password");
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
