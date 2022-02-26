package com.example.alijavapta.controller;

import com.example.alijavapta.config.CouponRecordStatus;
import com.example.alijavapta.config.RedisDistributeLock;
import com.example.alijavapta.config.ResponseCode;
import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.my.CouponMapper;
import com.example.alijavapta.mapper.my.RedisService;
import com.example.alijavapta.utils.SMSService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.Duration;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping(value = "/coupon")
public class CouponController {
    @Resource
    private CouponMapper couponMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private RedisDistributeLock redisDistributeLock;

    @PostMapping(value = "/receiveCoupon")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
            Exception.class, value = "myTransactionManager")
    public Response receiveCoupon(@RequestBody Condition condition) {
        if (redisService.getAndDeleteString(condition.getCode()) == null) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    "非法状态");
        }
        Coupon coupon = new Coupon();
        coupon.setCouponID(condition.getCouponID());
        coupon = couponMapper.GetCoupon(coupon);
        if (couponMapper.GetCouponRecordsCount(condition) >= coupon.getPer_limit()) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    "已达到最大领取数量");
        }
        CouponRecord record = new CouponRecord();
        record.setCoupon(coupon);
        Subject currUser = SecurityUtils.getSubject();
        record.setUser((User) currUser.getPrincipal());
        if (coupon.getValid_type().getCouponValidTypeID() == 1) {
            record.setValid_start_time(coupon.getValid_start_time());
            record.setValid_end_time(coupon.getValid_end_time());
        } else if (coupon.getValid_type().getCouponValidTypeID() == 2) {
            Date nowDate = new Date();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(nowDate);
            cal1.set(Calendar.HOUR, 0);
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.SECOND, 0);
            record.setValid_start_time(cal1.getTime());

            Calendar cal2 = (Calendar) cal1.clone();
            cal2.setTime(nowDate);
            cal2.set(Calendar.HOUR, 0);
            cal2.set(Calendar.MINUTE, 0);
            cal2.set(Calendar.SECOND, 0);
            cal2.add(Calendar.DAY_OF_YEAR, coupon.getValid_days());

            record.setValid_end_time(cal2.getTime());
        }
        int count = couponMapper.InsertCouponRecord(record);
        if (count > 0) {
            coupon.setTake_count(coupon.getTake_count().add(BigInteger.valueOf(1)));
            if (coupon.getTake_count().compareTo(coupon.getQuota()) > 0) {
                throw new RuntimeException("优惠券已领完");
            }
            count = couponMapper.UpdateCoupon(coupon);
            if (count > 0) {
                return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                        count, record);
            } else {
                throw new RuntimeException("优惠券领取失败");
            }
        } else {
            throw new RuntimeException("优惠券领取失败");
        }
    }

    @PostMapping(value = "/useCoupon")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
            Exception.class, value = "myTransactionManager")
    public Response useCoupon(@RequestBody CouponRecord record) {
        String uuid = UUID.randomUUID().toString();
        record.setUser((User) SecurityUtils.getSubject().getPrincipal());
        record = couponMapper.GetSingleCouponRecord(record);
        if (record == null) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    "您没有该优惠券");
        }
        try {
            // 获取锁
            Coupon coupon = new Coupon();
            coupon.setCouponID(record.getCoupon().getCouponID());
            coupon = couponMapper.GetCoupon(coupon);
            boolean lock =
                    redisDistributeLock.tryLock("COUPON_" + coupon.getCouponID(),
                            uuid, 5, TimeUnit.SECONDS);
            if (lock) {
                coupon.setUsed_count(coupon.getUsed_count().add(BigInteger.valueOf(1)));
                int count = couponMapper.UpdateCoupon(coupon);
                if (count > 0) {
                    record.setUse_time(new Date());
                    record.setUse_status(CouponRecordStatus.USED.getValue());
                    count = couponMapper.UpdateCouponRecord(record);
                    if (count > 0) {
                        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                                0, record);
                    } else {
                        throw new RuntimeException("更新优惠券记录失败");
                    }
                } else {
                    throw new RuntimeException("更新优惠券失败");
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
        return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                0, null);
    }

    @PostMapping(value = "/findAllCoupons")
    public Response findAllCoupons(@RequestBody Condition condition) {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                couponMapper.CountAllCoupons(condition),
                couponMapper.ListAllCoupons(condition));
    }

    @PostMapping(value = "/findUserCoupons")
    public Response findUserCoupons(@RequestBody Condition condition) {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                couponMapper.CountCoupons(condition),
                couponMapper.ListCoupons(condition));
    }

    @GetMapping("/{id}")
    public ModelAndView getCoupon(@PathVariable String id) {
        Coupon coupon = new Coupon();
        coupon.setCouponID(id);
        coupon = couponMapper.GetCoupon(coupon);
        if (coupon != null) {
            ModelAndView mv = new ModelAndView("coupon/index");
            String randomCode = SMSService.getNonce_str(10);
            redisService.setString(randomCode, randomCode,
                    Duration.ofMinutes(5));
            mv.addObject("code", randomCode);
            mv.addObject("coupon", coupon);
            return mv;
        } else {
            return new ModelAndView("404");
        }
    }

    @PostMapping("/createCoupon")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
            Exception.class, value = "myTransactionManager")
    public Response createCoupon(@RequestBody Coupon coupon) throws NoSuchAlgorithmException {
        int count = couponMapper.CreateCoupon(coupon);
        if (count > 0) {
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    coupon);
        } else {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                    "Failed to create coupon.");
        }
    }

    @PutMapping("/updateCoupon")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
            Exception.class, value = "myTransactionManager")
    public Response updateCoupon(@RequestBody Coupon coupon) throws NoSuchAlgorithmException {
        int count = couponMapper.UpdateCoupon(coupon);
        if (count > 0) {
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    coupon);
        } else {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                    "Failed to create coupon.");
        }
    }

    @DeleteMapping("/deleteCoupon")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
            Exception.class, value = "myTransactionManager")
    public Response deleteCoupon(@RequestBody Coupon coupon) {
        int count = couponMapper.DeleteCoupon(coupon);
        if (count > 0) {
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    null);
        } else {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                    "Failed to create coupon.");
        }
    }
}
