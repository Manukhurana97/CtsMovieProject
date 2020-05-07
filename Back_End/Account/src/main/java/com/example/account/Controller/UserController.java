package com.example.account.Controller;

import com.example.account.Dao.AddressDaoRepository;
import com.example.account.Dao.CartDaoRepositry;
import com.example.account.Dao.UserDaoRepository;
import com.example.account.Model.Cart;
import com.example.account.Model.User;
import com.example.account.Util;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.account.Response.serverResponse;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/User")
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
    @HystrixCommand(fallbackMethod = "userRegistrationFallback")
    public ResponseEntity<serverResponse> adduser(@Valid @RequestBody User Customerincomingdata) {
        serverResponse resp = new serverResponse();
        try {
            Customerincomingdata.setUserPermission("customer");
            User usr = userdao.saveAndFlush(Customerincomingdata);
            Cart cart = new Cart();
            cart.setUser(usr);
            usr.setCart(cart);
            createcart(cart);
            resp.setUsertype(usr.getUserPermission());
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Account Created Successfully");

        } catch (Exception e) {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setMessage("Failed to create account ");

        }

        return new ResponseEntity<serverResponse>(resp, HttpStatus.OK);
    }


    public Cart createcart(Cart cart) {
        Cart cat = cartdao.saveAndFlush(cart);
        return cat;
    }


    @PostMapping("/AdminRegister")
    @HystrixCommand(fallbackMethod = "adminRegistrationFallback")
    public ResponseEntity<serverResponse> addadmin(@Valid @RequestBody User adminincommingdata) {
        serverResponse resp = new serverResponse();
        try {
            adminincommingdata.setUserPermission("admin");
            User usr = userdao.saveAndFlush(adminincommingdata);
            Cart cart = new Cart();
            cart.setUser(usr);
            usr.setCart(cart);
            createcart(cart);
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Admin created Successfully");
            resp.setUsertype(usr.getUserPermission());

        } catch (Exception e) {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setMessage("Error Creating admin");
        }

        return new ResponseEntity<serverResponse>(resp, HttpStatus.OK);
    }

    @PostMapping("/UserLogin")
    @HystrixCommand(fallbackMethod = "UserLogingFallback")
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
    @HystrixCommand(fallbackMethod = "adminLogingFallback")
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

    @GetMapping("All_User/{id}")
    public ResponseEntity<serverResponse> find_all(@RequestHeader(name = "Authentication") String token, @PathVariable int id) {
        serverResponse response = new serverResponse();

        if (jwtutil.checkToken(token) != null) {
            try {
                jwtutil.isTokenExpired(token);

                try {
                    User usr = jwtutil.checkToken(token);
                    if (usr.getUserid() == id && usr.getUserPermission().equals("admin")) {
                        List<User> userlst = userdao.findAll();
                        response.setMessage("All data");
                        response.setStatus(HttpStatus.OK.value());
                        response.setUseralldata(userlst);
                    }
                    {
                        response.setMessage("Invalid Token or no valid permission ");
                        response.setStatus(HttpStatus.BAD_REQUEST.value());
                    }

                } catch (Exception e) {
                    response.setMessage("Invalid Token");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }

            } catch (Exception e) {
                response.setMessage("Token Expired");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }

        } else {
            response.setMessage("no token can't process further");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }


        return new ResponseEntity<serverResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<User> find_By_id(@PathVariable int id) {
        return userdao.findById(id);
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<serverResponse> deleteUser(@RequestHeader(name = "Authentication") String token, @PathVariable int id) {
        serverResponse response = new serverResponse();
        if (jwtutil.checkToken(token) != null) {
            try {
                jwtutil.isTokenExpired(token);

                try {
                    User usr = jwtutil.checkToken(token);
                    if (usr.getUserid() == id) {
                        userdao.deleteById(id);
                        response.setMessage("Deleted Successfully");
                        response.setStatus(HttpStatus.OK.value());
                    } else {
                        response.setMessage("Invalid Token");
                        response.setStatus(HttpStatus.BAD_REQUEST.value());
                    }

                } catch (Exception e) {
                    response.setMessage("Invalid Token");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }

            } catch (Exception e) {
                response.setMessage("Token Expired");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }

        } else {
            response.setMessage("no token can't process further");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }


        return new ResponseEntity<serverResponse>(response, HttpStatus.OK);
    }

    public User checkToken(String token) {
        User usr = null;
        boolean time;
        if (jwtutil.checkToken(token) != null) {
            time = jwtutil.isTokenExpired(token);
            if (!time)
                usr = jwtutil.checkToken(token);
        }
        return usr;
    }

    public ResponseEntity<serverResponse> userRegistrationFallback(@Valid @RequestBody User Customerincomingdata) {
        serverResponse response = new serverResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Could not Create Account  Right Now ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<serverResponse> adminRegistrationFallback(@Valid @RequestBody User adminincommingdata) {
        serverResponse response = new serverResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Could not Create Account  Right Now ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<serverResponse> UserLogingFallback(@Valid @RequestBody Map<String, String> credential) {
        serverResponse response = new serverResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Could not Login  Right Now ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<serverResponse> adminLogingFallback(@Valid @RequestBody Map<String, String> credential) {
        serverResponse response = new serverResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Could not Login  Right Now ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
