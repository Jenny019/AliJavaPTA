package com.example.alijavapta.domain;

import java.util.Date;

public class Coupon {
    private long couponID;
    private CouponCategory category;
    private String name;
    private float condition;
    private float amount;
    private Date startDate;
    private Date endDate;
    private String description;
    private String prerequisite;
    private int status = -1;

    public long getCouponID() {
        return couponID;
    }

    public void setCouponID(long couponID) {
        this.couponID = couponID;
    }

    public CouponCategory getCategory() {
        return category;
    }

    public void setCategory(CouponCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCondition() {
        return condition;
    }

    public void setCondition(float condition) {
        this.condition = condition;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
