package com.example.alijavapta.domain;

import com.example.alijavapta.config.RedisKey;

import java.util.Date;

public class Condition {
    private String userID;
    private String userName;
    private String phone;
    private int limit;
    private int offset;
    private int status = -1;
    private Date startDate;
    private Date endDate;
    private long categoryID = -1;
    private String title;
    private long productID = -1;
    private RedisKey redisKey;
    private int redisKeyVal = -1;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRedisKeyVal() {
        return redisKeyVal;
    }

    public void setRedisKeyVal(int redisKeyVal) {
        this.redisKeyVal = redisKeyVal;
        setRedisKey(RedisKey.values()[redisKeyVal]);
    }

    public RedisKey getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(RedisKey redisKey) {
        this.redisKey = redisKey;
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

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
