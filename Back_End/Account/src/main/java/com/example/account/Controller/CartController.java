package com.example.account.Controller;


import com.example.account.Dao.CartLIstManualRepositry;
import com.example.account.Dao.CartListRepository;
import com.example.account.Model.Cart;
import com.example.account.Model.CartItems;
import com.example.account.Model.Movie;
import com.example.account.Model.User;
import com.example.account.ApiCall.MovieData;
import com.example.account.Response.CartResponse;
import com.example.account.Response.PlaceOrderResponse;
import com.example.account.Util;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Cart")
@RefreshScope
public class CartController {

    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private CartListRepository cartListRepository;


    @Autowired
    private Util util;


    @Autowired
    private WebClient.Builder webClientBuilder;


    @Autowired
    private CartLIstManualRepositry cartLIstManualRepositry;


    @Autowired
    public MovieData movieData;


    @PostMapping("/Add_to_cart/{Productid}/{time}")
    @HystrixCommand(fallbackMethod = "getFallBackMethod")
    public ResponseEntity<CartResponse> add_to_cart(@RequestHeader(name = "Authentication") String token,
                                                    @PathVariable(value = "Productid") int Productid,
                                                    @PathVariable(value = "time") int time) {
        CartResponse resp = new CartResponse();

        if (util.checkToken(token) != null) {

            User usr = util.checkToken(token);
            Cart cart = usr.getCart();
            CartItems cartItems = new CartItems();
            System.out.println("cart:" + cart.getCartItems().isEmpty());

//  Api call
            Movie movie = movieData.getMovie(Productid);


            if (!cart.getCartItems().isEmpty()) {
                List<CartItems> movielist = cartListRepository.findByMovie_MovieidAndCart_Cartid(Productid, cart.getCartid());
                for (CartItems i : cart.getCartItems()) {
//                    if movie already in cart then add time and amount
                    if (!movielist.isEmpty()) {
                        resp.setStatus(HttpStatus.CONFLICT.value());
                        resp.setMessage("Item Already Exist");

                        break;
                    }
//                    Add new movie in existing list
                    else {
                        cartItems.setCart(cart);
                        cartItems.setMovie(movie);
                        cartItems.setTime((time));
                        cartItems.setAmount(movie.getPrice() * time);
                        cartLIstManualRepositry.saveandFlushMerge(cartItems);
                        System.out.println("new Item Added in existing cart");
                        resp.setStatus(HttpStatus.OK.value());
                        resp.setMessage("Added Successfully");

                        break;
                    }
                }
            } else {
//               Added new item to new List
                cartItems.setCart(cart);
                cartItems.setMovie(movie);
                cartItems.setTime(time);
                cartItems.setAmount((movie.getPrice() * time));
                cartLIstManualRepositry.saveandFlush(cartItems);
                System.out.println("new Item Added to new list");
                resp.setStatus(HttpStatus.OK.value());
                resp.setMessage("Added Successfully");


            }


        } else {
//            Unauthorized user
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setMessage("UnAuthorized");
        }
        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);

    }

    @PostMapping("/Updatecart/{Productid}/{time}")
    @Transactional
    public ResponseEntity<CartResponse> Reduct(@RequestHeader(name = "Authentication") String token, @PathVariable(value = "Productid") int Productid,
                                               @PathVariable(value = "time") int time) {
        CartResponse resp = new CartResponse();

        if (util.checkToken(token) != null) {
            User usr = util.checkToken(token);
            Cart cart = usr.getCart();
            System.out.println("cart:" + cart.getCartItems().isEmpty());

            if (!cart.getCartItems().isEmpty()) {
                for (CartItems i : cart.getCartItems()) {
                    if (i.getMovie().getMovieid() == Productid) {
                        if (time < 0) {
                            resp.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
                            resp.setMessage("Value less the 0");
                        }
//                            if time is 0 Delete from cart
                        else if (time == 0) {
                            System.out.println("data: " + i.getCart().getCartid());
                            cartListRepository.deleteCartItemsByCart_CartidAndMovie_Movieid(cart.getCartid(), Productid);
                            resp.setStatus(HttpStatus.OK.value());


                            List<CartItems> lst = cartListRepository.findAllByCart_Cartid(cart.getCartid());
                            if (lst.isEmpty()) {
                                resp.setMessage("Item Successfully Removed and cart is empty");
                            } else {
                                resp.setCartItems(lst);
                                resp.setMessage("Item remove successfully");
                            }
                        }
//                           change the amount and time of list of list
                        else {
                            i.setAmount((i.getAmount() / i.getTime()) * time);
                            i.setTime(time);
                            cartListRepository.saveAndFlush(i);
                            System.out.println("Change done in Existing cart ");
                            resp.setStatus(HttpStatus.OK.value());
                            resp.setMessage("Updated Successfully");
                            break;
                        }
                    } else {
                        System.out.println("Product No found");
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }

                }
            } else {
                System.out.println("Cart is empty");
                resp.setStatus(HttpStatus.NOT_FOUND.value());


            }

        } else {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setMessage("UnAuthorized");

        }
        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);

    }

    //     view item cart
    @PostMapping("/ViewCart")
    @Cacheable(value = "CartData", key = "'CartData'+#token")
    public ResponseEntity<CartResponse> ViewCart(@RequestHeader(name = "Authentication") String token) {
        CartResponse response = new CartResponse();
        logger.info("inside View cart ");
        if (util.checkToken(token) != null) {
            try {
                logger.info("View cart ");
                User usr = util.checkToken(token);
                Cart cart = usr.getCart();
                System.out.println(cart.getCartid());

                response.setStatus(HttpStatus.OK.value());
                response.setMessage(" cart data");
                response.setCartItems(cartListRepository.findAllByCart_Cartid(cart.getCartid()));

            } catch (Exception e) {

                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setMessage("fail");
            }
        } else {

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setMessage("Unauthorized ");
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // empty cart
    @PostMapping("/ClearCart")
    @Transactional
    public ResponseEntity<CartResponse> CleanCart(@RequestHeader(name = "Authentication") String token) {
        CartResponse response = new CartResponse();
        logger.info("inside Clear cart ");
        if (util.checkToken(token) != null) {
            try {
                logger.info("Clearning cart ");
                User usr = util.checkToken(token);
                Cart cart = usr.getCart();
                System.out.println(cart.getCartid());
                cartListRepository.deleteAllByCart_Cartid(cart.getCartid());

                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Cleared cart ");
                response.setCartItems(cartListRepository.findAllByCart_Cartid(cart.getCartid()));

            } catch (Exception e) {

                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setMessage("fail");
            }
        } else {

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setMessage("Unauthorized ");
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/PlaceorderCartData")
    public List<CartItems> placeorder(@RequestHeader(name = "Authentication") String token)
    {
        List<CartItems> cartItemsList = null;
        if (util.checkToken(token) != null) {
            try {
                User usr = util.checkToken(token);

                Cart cart = usr.getCart();
                cartItemsList = cartListRepository.findAllByCart_Cartid(cart.getCartid());

            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        return cartItemsList;
    }



        public ResponseEntity<CartResponse> getFallBackMethod(@RequestHeader(name = "Authentication") String token,
                                                          @PathVariable(value = "Productid") int Productid,
                                                          @PathVariable(value = "time") int time) {
        CartResponse response = new CartResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Could not connect to server right now ");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}





