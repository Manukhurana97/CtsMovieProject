package com.example.order.ApiCall;

import com.example.order.Model.CartItems;
import com.example.order.Model.User;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("Account")
public interface UserdataCall {

    @PostMapping("/User/checkToken")
    @LoadBalanced
     User checkToken(@RequestHeader(name = "Authentication") String token);

    @PostMapping("/Cart/PlaceorderCartData")
    @LoadBalanced
     List<CartItems> getcartdata(@RequestHeader(name = "Authentication") String token);

    @PostMapping("/Cart/ClearCart")
    @LoadBalanced
    void EmptyCart(@RequestHeader(name = "Authentication") String token);
}
