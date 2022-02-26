package com.example.alijavapta.schedule;

import com.example.alijavapta.config.CouponRecordStatus;
import com.example.alijavapta.config.CouponStatus;
import com.example.alijavapta.config.RedisDistributeLock;
import com.example.alijavapta.domain.Condition;
import com.example.alijavapta.domain.Coupon;
import com.example.alijavapta.domain.CouponRecord;
import com.example.alijavapta.mapper.my.CouponMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTask {
    @Resource
    private CouponMapper couponMapper;
    @Resource
    private RedisDistributeLock redisDistributeLock;

    @Scheduled(cron="0 0 0 * * ?")
    public void scanCoupons() {
        Condition condition = new Condition();
        condition.setStatus(1);
        condition.setValidEndTime(new Date());
        List<Coupon> couponList = couponMapper.ListAllCoupons(condition);
        for (Coupon coupon: couponList) {
            String uuid = UUID.randomUUID().toString();
            try {
                // 获取锁
                boolean lock =
                        redisDistributeLock.tryLock("COUPON_" + coupon.getCouponID(), uuid, 5, TimeUnit.SECONDS);
                if (lock) {
                    // 同步数据业务
                    if (coupon.getValid_end_time().before(new Date())) {
                        com.example.alijavapta.domain.CouponStatus status =
                                new com.example.alijavapta.domain.CouponStatus();
                        status.setCouponStatusID(CouponStatus.EXPIRED.getValue());
                        coupon.setStatus(status);
                        couponMapper.UpdateCoupon(coupon);
                    }
                } else {
                    System.out.println("fail to get redis lock, wait next time");
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                // 释放锁
                redisDistributeLock.releaseLock("COUPON_" + coupon.getCouponID(), uuid);
            }
        }
    }

    @Scheduled(cron="0 0 0 * * ?")
    public void scanCouponRecords() {
        Condition condition = new Condition();
        condition.setStatus(1);
        condition.setValidEndTime(new Date());
        List<CouponRecord> couponList =
                couponMapper.ListAllCouponRecords(condition);
        for (CouponRecord record: couponList) {
            String uuid = UUID.randomUUID().toString();

            try {
                // 获取锁
                boolean lock =
                        redisDistributeLock.tryLock("COUPON_RECORD_" + record.getCouponRecordID(), uuid, 5, TimeUnit.SECONDS);

                if (lock) {
                    // 同步数据业务
                    if (record.getValid_end_time().before(new Date())) {
                        record.setUse_status(CouponRecordStatus.EXPIRED.getValue());
                        couponMapper.UpdateCouponRecord(record);
                    }
                } else {
                    System.out.println("fail to get redis lock, wait next time");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                // 释放锁
                redisDistributeLock.releaseLock("COUPON_RECORD_" + record.getCouponRecordID(),
                    uuid);
            }
        }
    }
}