package com.example.alijavapta.domain;

import java.util.Date;

public class CouponRecord {
    private long couponRecordID;
    private User user;
    private Date createdAt;
    private Coupon coupon;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public long getCouponRecordID() {
        return couponRecordID;
    }

    public void setCouponRecordID(long couponRecordID) {
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
