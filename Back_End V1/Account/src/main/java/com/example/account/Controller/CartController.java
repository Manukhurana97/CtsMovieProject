package com.example.account.Controller;

import com.example.account.Dao.CartDaoRepositry;
import com.example.account.Dao.CartListRepository;
import com.example.account.Model.Cart;
import com.example.account.Model.CartItems;
import com.example.account.Model.Movie;
import com.example.account.Model.User;
import com.example.account.Response.CartResponse;
import com.example.account.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Cart")
public class CartController {

    public static Logger logger = Logger.getLogger(CartController.class.getName());

    @Autowired
    private CartDaoRepositry cartDaoRepositry;

    @Autowired
    private CartListRepository cartListRepository;


    @Autowired
    private Util util;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @PostMapping("/Add_to_cart/{Name}/{Productid}/{Quantity}/{Amount}")
    @ResponseBody
    public ResponseEntity<CartResponse> add_to_cart(@RequestHeader(name = "AUTH_TOKEN") String token, @PathVariable(value = "Name") String Name, @PathVariable(value = "Productid") int Productid,
                                                    @PathVariable(value = "Quantity") int Quantity, @PathVariable(value = "Amount") int Amount) throws IOException {
        CartResponse resp = new CartResponse();

        if (util.checkToken(token) != null)
        {
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
                for (CartItems i : cart.getCartItems()) {
                    if (i.getMovie().getId() == Productid) {
                        i.setAmount(i.getAmount() + (Amount * Quantity));
                        i.setQuantity(i.getQuantity() + Quantity);
                        cartListRepository.saveAndFlush(i);
                        System.out.println("Append to Existing cart ");
                        resp.setStatus(HttpStatus.OK.value());
                        resp.setMessage("Added Successfully");
                        resp.setAuth_Token(token);

                    } else {
                        cartItems.setCart(cart);
                        cartItems.setMovie(movie);
                        cartItems.setAmount(Quantity);
                        cartItems.setAmount(Amount);
                        cartListRepository.saveAndFlush(cartItems);
                        System.out.println("new Item Added in existing cart");
                        resp.setStatus(HttpStatus.OK.value());
                        resp.setMessage("Added Successfully");
                        resp.setAuth_Token(token);

                    }
                }
            } else {
                cartItems.setCart(cart);
                cartItems.setMovie(movie);

                cartItems.setAmount(Quantity);
                cartItems.setAmount(Amount);
                cartListRepository.saveAndFlush(cartItems);
                System.out.println("new Item Added to new list");
                resp.setStatus(HttpStatus.OK.value());
                resp.setMessage("Added Successfully");
                resp.setAuth_Token(token);

            }

        }
        else
            {
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                resp.setMessage("UnAuthorized");
                resp.setAuth_Token(token);
            }
        return new ResponseEntity<CartResponse>(resp, HttpStatus.ACCEPTED);

    }

    @PostMapping("/ViewCart")
    public ResponseEntity<CartResponse> add_to_cart(@RequestHeader(name = "AUTH_TOKEN") String token) {
        CartResponse response = new CartResponse();
        logger.info("inside cart ");
        if(util.checkToken(token)!=null)
        {
            try{
                User usr = util.checkToken(token);
                Cart cart = usr.getCart();
                List<CartItems> lst =  cartListRepository.findAllByCart_Cartid(cart.getCartid());
                response.setCartItems(lst);
                response.setAuth_Token(token);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Success");

            }
            catch (Exception e)
            {
                response.setAuth_Token(token);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setMessage("fail");

            }
        }
        else
        {}

    return new ResponseEntity<CartResponse>(response, HttpStatus.OK);
    }


    @PostMapping("/ClearCart")
    public ResponseEntity<CartResponse> Emptycart(@RequestHeader(name = "AUTH_TOKEN") String token) {
        CartResponse response = new CartResponse();
        logger.info("Empty cart ");
        CartResponse resp = new CartResponse();
        if(util.checkToken(token)!=null)
        {
            try{
                User usr = util.checkToken(token);
                Cart cart = usr.getCart();
                List<CartItems> lst = cartListRepository.findAllByCart_Cartid(cart.getCartid());
                for(int i=0;i<lst.size();i++)
                {
                    CartItems id = lst.get(i);
                    cartListRepository.deleteById(id.getId());
                }

                response.setAuth_Token(token);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Deleted successfully");

            }
            catch (Exception e)
            {
                response.setAuth_Token(token);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setMessage("Deleted successfully");
            }
        }
        else
            {
                response.setAuth_Token(token);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setMessage("Deleted successfully");
            }

        return new ResponseEntity<CartResponse>(resp, HttpStatus.OK);
    }


}