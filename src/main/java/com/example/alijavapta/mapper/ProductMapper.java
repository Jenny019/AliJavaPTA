package com.example.alijavapta.mapper;

import com.example.alijavapta.domain.*;

import java.util.List;

public interface ProductMapper {
    List<Product> ListProducts(Condition condition);
    List<ProductVariant> ListProductVariants(Condition condition);
}
