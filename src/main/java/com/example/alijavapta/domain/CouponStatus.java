package com.example.alijavapta.domain;

import java.io.Serializable;
import java.util.Date;

public class CouponStatus implements Serializable {
    private int couponStatusID;
    private String title;
    private Date createdAt;
    private Date updatedAt;

    public int getCouponStatusID() {
        return couponStatusID;
    }

    public void setCouponStatusID(int couponStatusID) {
        this.couponStatusID = couponStatusID;
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
