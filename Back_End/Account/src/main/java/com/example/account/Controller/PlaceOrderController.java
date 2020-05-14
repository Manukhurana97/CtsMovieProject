package com.example.account.Controller;

import com.example.account.Dao.CartListRepository;
//import com.example.account.Dao.OrderItemsRepository;
import com.example.account.Dao.OrderItemsRepository;
import com.example.account.Dao.UserOrderRepositry;
import com.example.account.Model.*;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Order")
@RefreshScope
public class PlaceOrderController {

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Autowired
    private CartListRepository cartListRepository;

    @Autowired
    private UserOrderRepositry userOrderRepositry;

    @Autowired
    private Util util;

    public static Logger logger = LoggerFactory.getLogger(PlaceOrderController.class);


    @PostMapping("/Placeorder")
    public ResponseEntity<PlaceOrderResponse> placeorder(@RequestHeader(name = "Authentication") String token) {

        PlaceOrderResponse response = new PlaceOrderResponse();

        if (util.checkToken(token) != null) {
            try {
                User usr = util.checkToken(token);
                Cart cart = usr.getCart();
                List<CartItems> cartItemsList = cartListRepository.findAllByCart_Cartid(cart.getCartid());
                if (cartItemsList.isEmpty()) {
                    response.setMessage("Cart Empty");
                    response.setStatus(HttpStatus.NO_CONTENT.value());
                } else {

                    OrdersItems ordersItems = new OrdersItems();
                    UserOrder userOrder = new UserOrder();

                    List<Movie> movieList = new ArrayList<>();
                    double Amount = 0;
                    for (int i = 0; i < cartItemsList.size(); i++) {
                        CartItems cartItems = cartItemsList.get(i);
                        movieList.add(cartItems.getMovie());
                        Amount += cartItems.getAmount();
                    }

                    Date date = new Date();
                    userOrder.setDate(date);
                    userOrder.setUser(usr);
                    userOrder.setAmount(Amount);
                    userOrder.setStatus("Confirmed");
                    UserOrder usercurrentOrder = userOrderRepositry.saveAndFlush(userOrder);

                    ordersItems.setUserOrder(usercurrentOrder);
                    ordersItems.setMovie(movieList);
                    orderItemsRepository.saveAndFlush(ordersItems);

                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Order Place successfully");
                }

            } catch (Exception e) {
                System.out.println(e);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setMessage("Can't Place order Right Now");
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Authentication fail");
        }


        return new ResponseEntity<PlaceOrderResponse>(response, HttpStatus.OK);
    }


    @PostMapping("/AllOrders")
    public ResponseEntity<PlaceOrderResponse> AllOrders(@RequestHeader(name = "Authentication") String token) {
        PlaceOrderResponse response = new PlaceOrderResponse();
        HttpStatus status = null;
        try {
            User usr = util.checkToken(token);
            if (usr == null)
                throw new UnsupportedOperationException();

            List<OrdersItems> ordersItemsList = orderItemsRepository.findAllByUserOrder_User(usr);
            if (ordersItemsList.size() == 0) {
                response.setMessage("Empty ");
                response.setStatus(HttpStatus.OK.value());
            } else {
                response.setMessage("Order data");
                response.setStatus(HttpStatus.OK.value());
                response.setOrdersItemsList(ordersItemsList);
            }
            status = HttpStatus.OK;

        } catch (Exception e) {
            response.setMessage("In valid User");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            status = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(response, status);

    }

}
