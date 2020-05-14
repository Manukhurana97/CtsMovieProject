package com.example.account.Response;

import com.example.account.Model.CartItems;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CartResponse {
    public int status;
    public String message;
    private String auth_Token;
    private List<CartItems> cartItems;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuth_Token() {
        return auth_Token;
    }

    public void setAuth_Token(String auth_Token) {
        this.auth_Token = auth_Token;
    }

    public List<CartItems> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItems> cartItems) {
        this.cartItems = cartItems;
    }
}
