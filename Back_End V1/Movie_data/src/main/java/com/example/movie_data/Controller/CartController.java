//package com.example.movie_data.Controller;
//
//import com.example.movie_data.Dao.CartDaoRepositry;
//import com.example.movie_data.Dao.CartListRepository;
//import com.example.movie_data.Model.Cart;
//import com.example.movie_data.Model.CartItems;
//import com.example.movie_data.Model.Movie;
//import com.example.movie_data.Model.User;
//import com.example.movie_data.Util;
//import org.hibernate.annotations.Any;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/Cart")
//public class CartController {
//
//    @Autowired
//    private CartDaoRepositry cartDaoRepositry;
//
//    @Autowired
//    private CartListRepository cartListRepository;
//
//    @Autowired
//    private Util util;
//
//    @PostMapping("/Add_to_cart")
//    public void add_to_cart(@RequestBody Movie movie, Integer quantity, Integer amount, @RequestHeader(name = "AUTH_TOKEN") String token) {
//        System.out.println("Movie: "+ movie);
//        if (util.checkToken(token) != null) {
//            User user = util.checkToken(token);
//            System.out.println("User: " + user);
//            Cart cart = user.getCart();
//            System.out.println("cart: " + cart);
//            if (cart.getCartItems() != null || !cart.getCartItems().isEmpty()) {
//                for (CartItems i : cart.getCartItems()) {
//                    if (i.getMovie().getId() == movie.getId()) {
//                        i.setAmount(i.getId() + amount);
//                    }
//                }
//            } else {
//                System.out.println("Null token");
//            }
//        }
//        else
//            {
//                System.out.println("NO token ");
//            }
//    }
//}
