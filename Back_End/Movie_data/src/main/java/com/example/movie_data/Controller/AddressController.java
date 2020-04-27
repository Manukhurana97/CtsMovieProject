package com.example.movie_data.Controller;

import com.example.movie_data.Dao.AddressDaoRepository;
import com.example.movie_data.Dao.UserDaoRepository;
import com.example.movie_data.Model.Address;
import com.example.movie_data.Model.User;
import com.example.movie_data.Response.addressResponse;
import com.example.movie_data.Response.userResponse;
import com.example.movie_data.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Address/")
public class AddressController {

    private static Logger logger = Logger.getLogger(AddressController.class.getName());

    @Autowired
    private UserController userController;

    @Autowired
    private UserDaoRepository userDaoRepository;

    @Autowired
    private AddressDaoRepository addressDaoRepository;

    @Autowired
    private Util util;

    @PostMapping("AddAddress")
    public ResponseEntity<userResponse> addAddress(@RequestBody Address address, @RequestHeader(name = "AUTH_TOKEN") String token)
    {
        userResponse userresp = new userResponse();
        if (util.checkToken(token)!=null)
        {
            try {
                User user = util.checkToken(token);
                user.setAddress(address);
                address.setUser(user);

                Address adr = addressDaoRepository.saveAndFlush(address);

                userresp.setStatus("200");
                userresp.setMessage("Address upload");
                userresp.setUser(user);
                userresp.setAddress(adr);
                userresp.setAuth_token(token);
            }
            catch (Exception e)
            {
                userresp.setStatus("200");
                userresp.setMessage("Address upload");
                userresp.setAuth_token(token);
            }
        }
        else
        {
            userresp.setStatus("500");
            userresp.setMessage("Error");
        }
        return new ResponseEntity<userResponse>(userresp, HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    public ResponseEntity<addressResponse> getAllAddress(@RequestHeader(name = "AUTH_TOKEN") String token)
    {
        addressResponse resp = new addressResponse();
        if(util.checkToken(token)!=null)
        {
            try {
                User usr = util.checkToken(token);

                Address addr = addressDaoRepository.findByUser(usr);

                HashMap<String, String> map = new HashMap<>();
                map.put("address", addr.getAddress());
                map.put("city", addr.getCity());
                map.put("state", addr.getState());
                map.put("country", addr.getCountry());
                map.put("zipcode", addr.getZipcode());
                map.put("phonenumber", addr.getPhonenumber());

                resp.setStatus("200");
                resp.setMessage("Success");
                resp.setAuth_Token(token);
                resp.setMap(map);
            }
            catch (Exception e)
            {
                resp.setAuth_Token(token);
                resp.setMessage(e.getMessage());
                resp.setStatus("500");
            }

        }
        else {
            resp.setStatus("500");
            resp.setMessage("Error");
        }

        return new ResponseEntity<addressResponse>(resp, HttpStatus.ACCEPTED);
    }

}
