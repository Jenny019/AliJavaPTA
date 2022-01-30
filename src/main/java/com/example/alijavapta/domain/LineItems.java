package com.example.alijavapta.domain;

public class LineItems {
    private long lineItemID;
    private Product product;
    private ProductVariant productVariant;
    private int quantity;
    private Order order;

    public long getLineItemID() {
        return lineItemID;
    }

    public void setLineItemID(long lineItemID) {
        this.lineItemID = lineItemID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
