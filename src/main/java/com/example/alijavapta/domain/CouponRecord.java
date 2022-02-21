package com.example.alijavapta.domain;

import java.io.Serializable;
import java.util.Date;

public class CouponRecord implements Serializable {
    private String couponRecordID;
    private Date valid_start_time;
    private Date valid_end_time;
    private Date receive_time;
    private int use_status;
    private Date use_time;
    private User user;
    private Date createdAt;
    private Coupon coupon;
    private Date updatedAt;

    public Date getValid_start_time() {
        return valid_start_time;
    }

    public void setValid_start_time(Date valid_start_time) {
        this.valid_start_time = valid_start_time;
    }

    public Date getValid_end_time() {
        return valid_end_time;
    }

    public void setValid_end_time(Date valid_end_time) {
        this.valid_end_time = valid_end_time;
    }

    public Date getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(Date receive_time) {
        this.receive_time = receive_time;
    }

    public int getUse_status() {
        return use_status;
    }

    public void setUse_status(int use_status) {
        this.use_status = use_status;
    }

    public Date getUse_time() {
        return use_time;
    }

    public void setUse_time(Date use_time) {
        this.use_time = use_time;
    }

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
