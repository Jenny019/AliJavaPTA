package com.example.alijavapta.domain;

import java.io.Serializable;
import java.util.Date;

public class CouponType implements Serializable {
    private String couponTypeID;
    private String title;
    private Date createdAt;
    private Date updatedAt;

    public String getCouponTypeID() {
        return couponTypeID;
    }

    public void setCouponTypeID(String couponTypeID) {
        this.couponTypeID = couponTypeID;
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
