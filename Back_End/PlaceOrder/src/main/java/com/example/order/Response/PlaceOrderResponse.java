package com.example.order.Response;

import com.example.order.Model.OrdersItems;
import lombok.Data;
import java.util.List;

@Data
public class PlaceOrderResponse {
    private int status;
    private String Message;
    private List<OrdersItems> ordersItemsList;
}
