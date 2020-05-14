package com.example.order.Controller;

import com.example.order.ApiCall.UserdataCall;
import com.example.order.Dao.OrderItemsRepository;
import com.example.order.Dao.UserOrderRepositry;
import com.example.order.Model.*;
import com.example.order.Response.PlaceOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/Orders")
public class PlaceOrderController {

    @Autowired
    OrderItemsRepository orderItemsRepository;


    @Autowired
    private UserOrderRepositry userOrderRepositry;


    @Autowired
    private UserdataCall userdataCall;



    public static Logger logger = LoggerFactory.getLogger(PlaceOrderController.class);


    public User getUser(String token) {
        return userdataCall.checkToken(token);
    }


    public List<CartItems> getcartdata(String token) {
        return userdataCall.getcartdata(token);
    }


    @PostMapping("/Placeorder")
    public ResponseEntity<PlaceOrderResponse> placeorder(@RequestHeader(name = "Authentication") String token) {
        PlaceOrderResponse response = new PlaceOrderResponse();

        try {

//            User Api call
            User usr = getUser(token);
//            Movie data api call
            List<CartItems> cartItemsList = getcartdata(token);


            if (cartItemsList.isEmpty()) {
                response.setMessage("Cart Empty");
                response.setStatus(HttpStatus.NO_CONTENT.value());
            } else {

                OrdersItems ordersItems = new OrdersItems();
                UserOrder userOrder = new UserOrder();

                List<Movie> movieList = new ArrayList<>();
                double Amount = 0;
                for (CartItems cartItems : cartItemsList) {
                    movieList.add(cartItems.getMovie());
                    Amount += cartItems.getAmount();
                }


                Date date = new Date();
                userOrder.setDate(date);
                userOrder.setUserid(usr.userid);
                userOrder.setAmount(Amount);
                userOrder.setStatus("Confirmed");
                UserOrder currentOrder = userOrderRepositry.saveAndFlush(userOrder);

                ordersItems.setUserOrder(currentOrder);
                ordersItems.setMovie(movieList);
                orderItemsRepository.saveAndFlush(ordersItems);
                userdataCall.EmptyCart(token);

                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Order Place successfully");
            }

        } catch (Exception e) {
            System.out.println(e);
            logger.trace("cant place order ");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Can't Place order Right Now");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/AllOrders")
    public ResponseEntity<PlaceOrderResponse> AllOrders(@RequestHeader(name = "Authentication") String token) {
        PlaceOrderResponse response = new PlaceOrderResponse();
        HttpStatus status = null;
        try {
            User usr = userdataCall.checkToken(token);
            if (usr == null)
                throw new UnsupportedOperationException();

            List<OrdersItems> ordersItemsList = orderItemsRepository.findAllByUserOrder_Userid(usr.userid);
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

//    //    fallback method
//    public ResponseEntity<PlaceOrderResponse> PlaceOrderfallback(@RequestHeader(name = "Authentication") String token) {
//        PlaceOrderResponse response = new PlaceOrderResponse();
//        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        response.setMessage("Cant place order Right now");
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    public ResponseEntity<PlaceOrderResponse> Orderdatafallback(@RequestHeader(name = "Authentication") String token) {
//        PlaceOrderResponse response = new PlaceOrderResponse();
//        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        response.setMessage("Cant place order Right now");
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}