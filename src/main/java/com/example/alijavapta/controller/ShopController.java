package com.example.alijavapta.controller;

import com.example.alijavapta.domain.Condition;
import com.example.alijavapta.domain.Coupon;
import com.example.alijavapta.domain.ProductCategory;
import com.example.alijavapta.domain.User;
import com.example.alijavapta.mapper.my.CouponMapper;
import com.example.alijavapta.mapper.my.ProductMapper;
import com.example.alijavapta.mapper.my.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController()
@RequestMapping(path = "/shop")
public class ShopController {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CouponMapper couponMapper;

    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("shop/index");
        Subject currUser = SecurityUtils.getSubject();
        List<ProductCategory> productCategoryList =
                productMapper.ListProductCategories(new ProductCategory());
        Condition couponCondition = new Condition();
        couponCondition.setUserID(((User) currUser.getPrincipal()).getId());
        couponCondition.setStartDate(new Date());
        couponCondition.setEndDate(new Date());
        couponCondition.setStatus(1);
        List<Coupon> couponList = couponMapper.ListAllCouponsWithStates(couponCondition);
        mv.addObject("categories", productCategoryList);
        mv.addObject("coupons", couponList);
        return mv;
    }
}
