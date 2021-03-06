package com.example.alijavapta.mapper.my;

import com.example.alijavapta.domain.Condition;
import com.example.alijavapta.domain.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> ListOrders(Condition condition);

    int CreateOrder(Order order);
    int UpdateOrder(Order order);
    int DeleteOrder(Order order);
}
