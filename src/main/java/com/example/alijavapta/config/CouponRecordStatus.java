package com.example.alijavapta.config;

public enum CouponRecordStatus {
    USED(1),
    UNUSED(2),
    EXPIRED(3);

    private int value;

    CouponRecordStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
