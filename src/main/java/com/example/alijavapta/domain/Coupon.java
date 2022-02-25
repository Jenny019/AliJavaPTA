package com.example.alijavapta.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class Coupon implements Serializable {
    private String couponID;
    private CouponCategory discount_type;
    private String title;
    private Date startDate;
    private Date endDate;
    private CouponStatus status;
    private Date createdAt;
    private Date updatedAt;
    private Date valid_start_time;
    private Date valid_end_time;
    private String icon;
    private CouponType type;
    private String with_sn;
    private BigInteger with_amount;
    private BigInteger used_amount;
    private int per_limit;
    private BigInteger quota;
    private BigInteger take_count;
    private BigInteger used_count;
    private Date start_time;
    private Date end_time;
    private CouponValidType valid_type;
    private int valid_days;
    private User create_user;
    private User update_user;
    private CouponRecord record;
    private long version;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public CouponRecord getRecord() {
        return record;
    }

    public void setRecord(CouponRecord record) {
        this.record = record;
    }

    public String getCouponID() {
        return couponID;
    }

    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public CouponStatus getStatus() {
        return status;
    }

    public void setStatus(CouponStatus status) {
        this.status = status;
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

    public CouponCategory getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(CouponCategory discount_type) {
        this.discount_type = discount_type;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public String getWith_sn() {
        return with_sn;
    }

    public void setWith_sn(String with_sn) {
        this.with_sn = with_sn;
    }

    public BigInteger getWith_amount() {
        return with_amount;
    }

    public void setWith_amount(BigInteger with_amount) {
        this.with_amount = with_amount;
    }

    public BigInteger getUsed_amount() {
        return used_amount;
    }

    public void setUsed_amount(BigInteger used_amount) {
        this.used_amount = used_amount;
    }

    public int getPer_limit() {
        return per_limit;
    }

    public void setPer_limit(int per_limit) {
        this.per_limit = per_limit;
    }

    public BigInteger getQuota() {
        return quota;
    }

    public void setQuota(BigInteger quota) {
        this.quota = quota;
    }

    public BigInteger getTake_count() {
        return take_count;
    }

    public void setTake_count(BigInteger take_count) {
        this.take_count = take_count;
    }

    public BigInteger getUsed_count() {
        return used_count;
    }

    public void setUsed_count(BigInteger used_count) {
        this.used_count = used_count;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public CouponValidType getValid_type() {
        return valid_type;
    }

    public void setValid_type(CouponValidType valid_type) {
        this.valid_type = valid_type;
    }

    public int getValid_days() {
        return valid_days;
    }

    public void setValid_days(int valid_days) {
        this.valid_days = valid_days;
    }

    public User getCreate_user() {
        return create_user;
    }

    public void setCreate_user(User create_user) {
        this.create_user = create_user;
    }

    public User getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(User update_user) {
        this.update_user = update_user;
    }
}
