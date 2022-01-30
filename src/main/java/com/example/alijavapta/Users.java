package com.example.alijavapta;

import javax.persistence.*;

@Entity
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID", nullable = false)
    private long userId;
    @Basic
    @Column(name = "userName", nullable = false, length = 255)
    private String userName;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "phone", nullable = true, length = 15)
    private String phone;
    @Basic
    @Column(name = "email", nullable = true, length = 255)
    private String email;
    @Basic
    @Column(name = "avatar", nullable = true, length = 500)
    private String avatar;
    @Basic
    @Column(name = "introduction", nullable = true, length = 1000)
    private String introduction;
    @Basic
    @Column(name = "couponWalletID", nullable = false)
    private long couponWalletId;
    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public long getCouponWalletId() {
        return couponWalletId;
    }

    public void setCouponWalletId(long couponWalletId) {
        this.couponWalletId = couponWalletId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
