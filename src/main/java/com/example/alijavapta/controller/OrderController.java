package com.example.alijavapta.controller;

import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.my.OrderMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/order")
public class OrderController {
    @Resource
    private OrderMapper orderMapper;

    @RequestMapping(value = "/findAllOrder")
    public Iterable<Order> findAllOrder(Condition condition) {
        return orderMapper.ListOrders(condition);
    }

    @RequestMapping("/deleteOrder")
    public int deleteUser(Order order) {
        return orderMapper.DeleteOrder(order);
    }

    @RequestMapping("/createOrder")
    public int createOrder(Order order) {
        return orderMapper.CreateOrder(order);
    }
}
