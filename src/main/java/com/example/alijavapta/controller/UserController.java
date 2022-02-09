package com.example.alijavapta.controller;

import com.example.alijavapta.config.RedisKey;
import com.example.alijavapta.config.ResponseCode;
import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.UserMapper;
import com.example.alijavapta.shiro.MyFormAuthenticationFilter;
import com.example.alijavapta.utils.IGlobalCache;
import com.example.alijavapta.utils.SMSService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private IGlobalCache globalCache;

    @RequestMapping(value="/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/findAllUser")
    @RequiresRoles("管理员")
    @RequiresPermissions("仪表盘") // 权限管理.
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
    public Iterable<Role> findRoles(User user) {
        return userMapper.ListRoles(user);
    }

    @RequestMapping(value = "/findPermissions")
    public Iterable<Permission> findPermissions(Role role) {
        return userMapper.ListPermissions(role);
    }

    @PostMapping("/logout")
    public Response logout() {
        Subject currUser = SecurityUtils.getSubject();
        currUser.logout();
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", null);
    }

    @PostMapping("/login")
    public Response login(@RequestBody User user) throws NoSuchAlgorithmException {
        Subject currUser = SecurityUtils.getSubject();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(user.getPassword()));
        String encrypted = String.format("%032x", new BigInteger(1,
                md5.digest()));
        user.setPassword(encrypted);
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        try {
            currUser.login(new UsernamePasswordToken(user.getUserName(),
                    user.getPassword(), true));
        } catch (UnknownAccountException uae) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                    "账户不存在");
        } catch (IncorrectCredentialsException ice) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                    "密码不正确");
        } catch (LockedAccountException lae) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                    "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                    "用户名或密码错误次数过多，请十分钟后再试");
        } catch (AuthenticationException ae) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                    "未知错误，请重试");
        }

        if (currUser.isAuthenticated()) {
            Session session = currUser.getSession();
            //下面的是自定义的代码，随你怎么写
            session.setAttribute("userInfo", currUser.getPrincipal());
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", currUser.getPrincipal());
        }
        return new Response(ResponseCode.FAIL.ordinal(), "FAIL", "UserName " +
                "or password not correct");
    }

    @RequestMapping("/info")
    public Response Info(User user) {
        user.setRoles(userMapper.ListRoles(user));
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", user);
    }

    @PostMapping("/createUser")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
            Exception.class, value = "transactionManager")
    public Response createUser(@RequestBody User user) throws NoSuchAlgorithmException {
        if (userMapper.GetUser(user) != null) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                    "userName or phone exists. Please login.");
        }
        String key =
                RedisKey.REGISTER_USER_KEY.toString() + '-' + user.getPhone();
        if (user.getCode() != null && user.getCode().equals(globalCache.get(key))) {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(user.getPassword()));
            String encrypted = String.format("%032x", new BigInteger(1,
                    md5.digest()));
            user.setPassword(encrypted);
            userMapper.CreateUser(user);
            userMapper.CreateProperty(user);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", null);
        } else {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                    "Verification codes do not match.");
        }
    }

    @PutMapping("/updateUser")
    public int updateUser(User user) {
        return userMapper.UpdateUser(user);
    }

    @PutMapping("/resetPassword")
    public Response resetPassword(@RequestBody User user) throws NoSuchAlgorithmException {
        String key =
                RedisKey.FORGET_PASSWORD.toString() + '-' + user.getPhone();
        if (user.getCode() != null && user.getCode().equals(globalCache.get(key))) {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(user.getPassword()));
            String encrypted = String.format("%032x", new BigInteger(1,
                    md5.digest()));
            user.setPassword(encrypted);
            userMapper.ResetPassword(user);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                    null);
        }
        return new Response(ResponseCode.FAIL.ordinal(), "FAIL",
                "Verification codes do not match.");
    }

    @RequestMapping("/deleteUser")
    public int deleteUser(User user) {
        return userMapper.DeleteUser(user);
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @PostMapping("/getSMSCode")
    public Response getSMSCode(@RequestBody Condition condition) {
        String code = SMSService.getNonce_str(6);
        String key =
                condition.getRedisKey().toString() + '-' + condition.getPhone();
        globalCache.set(key, code);
        globalCache.expire(key, 5 * 60);
        System.out.println(key + ", " + globalCache.get(key));
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", null);
    }
}
