package com.example.alijavapta.controller;

import com.example.alijavapta.config.ResponseCode;
import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Resource
    private UserMapper userMapper;

    @RequestMapping(value = "/findAllUser")
    public Iterable<User> getAllUser() {
        return userMapper.ListUsers();
    }

    @RequestMapping(value = "/findCoupons")
    public Iterable<CouponRecord> findCoupons(Condition condition) {
        return userMapper.ListCoupons(condition);
    }

    @RequestMapping(value = "/findAddresses")
    public Iterable<Address> findAddress(Condition condition) {
        return userMapper.ListAddresses(condition);
    }

    @RequestMapping(value = "/findRoles")
    public Iterable<Role> findRoles(Condition condition) {
        return userMapper.ListRoles(condition);
    }

    @PostMapping("/login")
    public Response login(@RequestBody User user) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(user.getPassword()));
        String encrypted = String.format("%032x", new BigInteger(1,
                md5.digest()));
        user.setPassword(encrypted);
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        user = userMapper.Login(user);
        System.out.println(user);
        if (user != null)
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", user);
        return new Response(ResponseCode.FAIL.ordinal(), "FAIL", "UserName or" +
                " password not correct");
    }

    @RequestMapping("/info")
    public Response Info(User user)
    {
        Condition condition = new Condition();
        condition.setUserID(user.getId());
        user.setRoles(userMapper.ListRoles(condition));
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", user);
    }

    @PostMapping("/createUser")
    public Response createUser(@RequestBody  User user) throws NoSuchAlgorithmException {
        if (userMapper.GetUser(user) != null) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                    "userName or phone exists. Please login.");
        }
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(user.getPassword()));
        String encrypted = String.format("%032x", new BigInteger(1,
                md5.digest()));
        user.setPassword(encrypted);
        userMapper.CreateUser(user);
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", null);
    }

    @RequestMapping("/updateUser")
    public int updateUser(User user) {
        return userMapper.UpdateUser(user);
    }

    @RequestMapping("/deleteUser")
    public int deleteUser(User user) {
        return userMapper.DeleteUser(user);
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
