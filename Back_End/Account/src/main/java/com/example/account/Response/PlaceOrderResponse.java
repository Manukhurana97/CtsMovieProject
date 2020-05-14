package com.example.account.Response;

import com.example.account.Model.OrdersItems;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderResponse {
    private int status;
    private String Message;
    private List<OrdersItems> ordersItemsList;
}
