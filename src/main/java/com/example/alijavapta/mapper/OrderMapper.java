package com.example.alijavapta.mapper;

import com.example.alijavapta.domain.Condition;
import com.example.alijavapta.domain.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> ListOrders(Condition condition);
}
