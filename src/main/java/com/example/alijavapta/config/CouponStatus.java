package com.example.alijavapta.config;

public enum CouponStatus {
    VALID(1),
    INVALID(2),
    EXPIRED(3);

    private int value;

    CouponStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
