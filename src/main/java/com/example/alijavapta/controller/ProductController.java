package com.example.alijavapta.controller;

import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.my.ProductMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = "*")
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

    @RequestMapping("/deleteProduct")
    public int deleteUser(Product product) {
        return productMapper.DeleteProduct(product);
    }

    @RequestMapping("/updateProduct")
    public int updateProduct(Product product) {
        return productMapper.UpdateProduct(product);
    }

    @RequestMapping("/createProduct")
    public int createProduct(Product product) {
        return productMapper.CreateProduct(product);
    }
}
