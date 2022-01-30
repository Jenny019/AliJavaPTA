package com.example.alijavapta.domain;

import java.util.List;

public class Product {
    private long productID;
    private String title;
    private ProductCategory category;
    private String description;
    private String image;
    private int status = -1;
    private List<ProductVariant> variantList;

    public List<ProductVariant> getVariantList() {
        return variantList;
    }

    public void setVariantList(List<ProductVariant> variantList) {
        this.variantList = variantList;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
