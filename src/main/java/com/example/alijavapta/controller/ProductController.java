package com.example.alijavapta.controller;

import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.ProductMapper;
import com.example.alijavapta.mapper.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProductController {
    @Resource
    private ProductMapper productMapper;

    @RequestMapping(value = "/findAllProduct")
    public Iterable<Product> getAllProduct(Condition condition) {
        return productMapper.ListProducts(condition);
    }

    @RequestMapping(value = "/findProductVariant")
    public Iterable<ProductVariant> getAllProductVariant(Condition condition) {
        return productMapper.ListProductVariants(condition);
    }
}
