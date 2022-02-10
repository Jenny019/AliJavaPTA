package com.example.alijavapta.domain;

import java.io.Serializable;
import java.util.Date;

public class CouponRecord implements Serializable {
    private String couponRecordID;
    private User user;
    private Date createdAt;
    private Coupon coupon;
    private Date updatedAt;

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getCouponRecordID() {
        return couponRecordID;
    }

    public void setCouponRecordID(String couponRecordID) {
        this.couponRecordID = couponRecordID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createAt) {
        this.createdAt = createAt;
    }
}
