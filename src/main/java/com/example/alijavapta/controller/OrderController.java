package com.example.alijavapta.controller;

import com.example.alijavapta.domain.Condition;
import com.example.alijavapta.domain.Order;
import com.example.alijavapta.domain.Product;
import com.example.alijavapta.domain.ProductVariant;
import com.example.alijavapta.mapper.OrderMapper;
import com.example.alijavapta.mapper.ProductMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderMapper orderMapper;

    @RequestMapping(value = "/findAllOrder")
    public Iterable<Order> findAllOrder(Condition condition) {
        return orderMapper.ListOrders(condition);
    }
}
