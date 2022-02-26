package com.example.alijavapta.mapper.my;

import com.example.alijavapta.domain.*;

import java.util.List;

public interface CouponMapper {
    int CountCoupons(Condition condition);
    int CountAllCoupons(Condition condition);

    List<Coupon> ListAllCoupons(Condition condition);
    List<CouponRecord> ListCoupons(Condition condition);
    List<CouponRecord> ListAllCouponRecords(Condition condition);

    Coupon GetCoupon(Coupon coupon);
    int CreateCoupon(Coupon coupon);
    int UpdateCoupon(Coupon coupon);
    int DeleteCoupon(Coupon coupon);
    List<Coupon> ListAllCouponsWithStates(Condition condition);

    int InsertCouponRecord(CouponRecord record);
    int GetCouponRecordsCount(Condition condition);
    int UpdateCouponRecord(CouponRecord record);
    CouponRecord GetSingleCouponRecord(CouponRecord record);
}
