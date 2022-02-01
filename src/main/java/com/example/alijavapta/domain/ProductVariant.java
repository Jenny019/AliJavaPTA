package com.example.alijavapta.domain;

public class ProductVariant {
    private long productVariantID;
    private String name;
    private String value;
    private String image;
    private float price;
    private Product product;
    private int status = -1; // 0 待上架 1 上架 2 删除

    public long getProductVariantID() {
        return productVariantID;
    }

    public void setProductVariantID(long productVariantID) {
        this.productVariantID = productVariantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
