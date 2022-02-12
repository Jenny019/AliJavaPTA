package com.example.alijavapta.mapper.my;

import com.example.alijavapta.domain.*;

import java.util.List;

public interface ProductMapper {
    List<Product> ListProducts(Condition condition);
    List<ProductVariant> ListProductVariants(Condition condition);

    int CreateProduct(Product product);
    int UpdateProduct(Product product);
    int DeleteProduct(Product product);
}
