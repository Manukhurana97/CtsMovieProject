package com.example.account.Controller;


import com.example.account.Dao.CartLIstManualRepositry;
import com.example.account.Dao.CartListRepository;
import com.example.account.Model.Cart;
import com.example.account.Model.CartItems;
import com.example.account.Model.Movie;
import com.example.account.Model.User;
import com.example.account.Response.CartResponse;
import com.example.account.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.PreRemove;
import javax.transaction.Transactional;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Cart")
public class CartController {

    public static Logger logger = LoggerFactory.getLogger(CartController.class);


    @Autowired
    private CartListRepository cartListRepository;


    @Autowired
    private Util util;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private CartLIstManualRepositry cartLIstManualRepositry;


    @PostMapping("/Add_to_cart/{Productid}/{time}")
    public ResponseEntity<CartResponse> add_to_cart(@RequestHeader(name = "AUTH_TOKEN") String token,
                                                    @PathVariable(value = "Productid") int Productid,
                                                    @PathVariable(value = "time") int time) {
        CartResponse resp = new CartResponse();

        if (util.checkToken(token) != null) {
            User usr = util.checkToken(token);
            Cart cart = usr.getCart();
            CartItems cartItems = new CartItems();
            System.out.println("cart:" + cart.getCartItems().isEmpty());


//            Api call
            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://192.168.31.241:8000/" + Productid)
                    .retrieve().bodyToMono(Movie.class)
                    .block();
            System.out.println("all data:  " + movie);


            if (!cart.getCartItems().isEmpty()) {
                List<CartItems> ml = cartListRepository.findByMovie_MovieidAndCart_Cartid(Productid, cart.getCartid());
                for (CartItems i : cart.getCartItems()) {
//                    if movie already in cart then add time and amount
                    if (!ml.isEmpty()) {
                        resp.setStatus(HttpStatus.CONFLICT.value());
                        resp.setMessage("Item Already Exist");
                        resp.setAuth_Token(token);
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
                        resp.setAuth_Token(token);
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
                resp.setAuth_Token(token);

            }

        } else {
//            Unauthorized user
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setMessage("UnAuthorized");
            resp.setAuth_Token(token);
        }
        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);

    }

    @PostMapping("/Updatecart/{Productid}/{time}")
    @Transactional
    public ResponseEntity<CartResponse> Reduct(@RequestHeader(name = "AUTH_TOKEN") String token, @PathVariable(value = "Productid") int Productid,
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

                            resp.setAuth_Token(token);
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
                            resp.setAuth_Token(token);
                            break;
                        }
                    } else {
                        System.out.println("Product No found");
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                    resp.setAuth_Token(token);
                }
            } else {
                System.out.println("Cart is empty");
                resp.setStatus(HttpStatus.NOT_FOUND.value());
                resp.setAuth_Token(token);

            }

        } else {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setMessage("UnAuthorized");
            resp.setAuth_Token(token);
        }
        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);

    }

    //     view item cart
    @PostMapping("/ViewCart")
    public ResponseEntity<CartResponse> ViewCart(@RequestHeader(name = "AUTH_TOKEN") String token) {
        CartResponse response = new CartResponse();
        logger.info("inside cart ");
        if (util.checkToken(token) != null) {
            try {
                User usr = util.checkToken(token);
                Cart cart = usr.getCart();
                List<CartItems> lst = cartListRepository.findAllByCart_Cartid(cart.getCartid());
                if(lst.isEmpty())
                {
                    response.setMessage("Cart is Empty");
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setAuth_Token(token);
                }
                else {
                    response.setCartItems(lst);
                    response.setAuth_Token(token);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Success");
                }

            } catch (Exception e) {
                response.setAuth_Token(token);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setMessage("fail");
            }
        } else {
            response.setAuth_Token(token);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setMessage("Unauthorized ");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // empty cart
    @PostMapping("/ClearCart")
    @Transactional
    public ResponseEntity<CartResponse> CleanCart(@RequestHeader(name = "AUTH_TOKEN") String token) {
        CartResponse response = new CartResponse();
        logger.info("inside Clear cart ");
        if (util.checkToken(token) != null) {
            try {
                logger.info("Clearning cart ");
                User usr = util.checkToken(token);
                Cart cart = usr.getCart();
                System.out.println(cart.getCartid());
                cartListRepository.deleteAllByCart_Cartid(cart.getCartid());
                response.setAuth_Token(token);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Cleared cart ");
                response.setCartItems(cartListRepository.findAllByCart_Cartid(cart.getCartid()));

            } catch (Exception e) {
                response.setAuth_Token(token);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setMessage("fail");
            }
        } else {
            response.setAuth_Token(token);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setMessage("Unauthorized ");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}





