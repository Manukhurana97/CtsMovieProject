package com.example.account.Dao;

import com.example.account.Model.CartItems;

public interface CartLIstManualRepositry {
    public CartItems saveandFlush(CartItems cartItems);

    public CartItems saveandFlushUpdate(CartItems cartItems);

    public CartItems saveandFlushMerge(CartItems cartItems);

    public void deletebycartid(int cartid);


}
