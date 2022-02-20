package com.example.alijavapta.mapper.my;

import com.example.alijavapta.domain.*;

import java.util.List;

public interface ProductMapper {
    List<Product> ListProducts(Condition condition);
    int CountProducts(Condition condition);
    List<ProductVariant> ListProductVariants(Condition condition);
    int CountProductVariants(Condition condition);
    List<ProductCategory> ListProductCategories(ProductCategory category);
    int CountProductCategories(ProductCategory category);

    int CreateProduct(Product product);
    Product GetProduct(Product product);
    int UpdateProduct(Product product);
    int DeleteProduct(Product product);

    int CreateProductVariant(ProductVariant variant);
    int UpdateProductVariant(ProductVariant variant);
    int DeleteProductVariant(ProductVariant variant);
    int DeleteAllProductVariants(Product product);

    int CreateProductCategory(ProductCategory category);
    int UpdateProductCategory(ProductCategory category);
    int DeleteProductCategory(ProductCategory category);
}
