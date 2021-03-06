package com.example.alijavapta.domain;

import com.example.alijavapta.config.RedisKey;

import java.util.Calendar;
import java.util.Date;

public class Condition {
    private Date validEndTime;
    private String userID;
    private String couponID;
    private String code;
    private String userName;
    private String phone;
    private String roleID;
    private int limit;
    private int page;
    private int offset;
    private int status = -1;
    private Date startDate;
    private Date endDate;
    private long categoryID = -1;
    private String title;
    private String productID;
    private RedisKey redisKey;
    private int redisKeyVal = -1;
    private int transactionType = -1;
    private String sign;

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public String getCouponID() {
        return couponID;
    }

    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        this.setOffset((page - 1) * this.limit);
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

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

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        this.startDate = calendar.getTime();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        this.endDate = calendar.getTime();
    }
}
