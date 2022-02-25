package com.example.alijavapta.domain;

import java.io.Serializable;
import java.util.Date;

public class CouponValidType implements Serializable {
    private int couponValidTypeID;
    private String title;
    private Date createdAt;
    private Date updatedAt;

    public int getCouponValidTypeID() {
        return couponValidTypeID;
    }

    public void setCouponValidTypeID(int couponValidTypeID) {
        this.couponValidTypeID = couponValidTypeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
