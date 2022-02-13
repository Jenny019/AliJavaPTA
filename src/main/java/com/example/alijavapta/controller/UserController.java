package com.example.alijavapta.controller;

import com.example.alijavapta.config.RedisKey;
import com.example.alijavapta.config.ResponseCode;
import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.alipay.AlipayMapper;
import com.example.alijavapta.mapper.bank.BankMapper;
import com.example.alijavapta.mapper.my.UserMapper;
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
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Resource
    private UserMapper userMapper;

    @Resource
    private BankMapper bankMapper;

    @Resource
    private AlipayMapper alipayMapper;

    @Autowired
    private IGlobalCache globalCache;

    @RequestMapping(value="/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        Subject currUser = SecurityUtils.getSubject();
//        mv.addObject("currUser", currUser.getPrincipals());
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/findAllUser")
    @RequiresRoles("管理员")
    @RequiresPermissions("仪表盘") // 权限管理.
    public Response getAllUser() {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                userMapper.CountUsers(),
                userMapper.ListUsers());
    }

    @RequestMapping(value = "/findCoupons")
    public Response findCoupons(Condition condition) {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                userMapper.CountCoupons(),
                userMapper.ListCoupons(condition));
    }

    @RequestMapping(value = "/findAddresses")
    public Response findAddress(Condition condition) {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                userMapper.CountAddresses(),
                userMapper.ListAddresses(condition));
    }

    @RequestMapping(value = "/findRoles")
    public Response findRoles(User user) {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                0,
                userMapper.ListRoles(user));
    }

    @RequestMapping(value = "/findPermissions")
    public Response findPermissions(Role role) {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                0,
                userMapper.ListPermissions(role));
    }

    static class TransactionData {
        private List<Transaction> list;
        private long income;
        private long expense;
        private long balance;

        public long getBalance() {
            return balance;
        }

        public void setBalance(long balance) {
            this.balance = balance;
        }

        public List<Transaction> getList() {
            return list;
        }

        public void setList(List<Transaction> list) {
            this.list = list;
        }

        public long getIncome() {
            return income;
        }

        public void setIncome(long income) {
            this.income = income;
        }

        public long getExpense() {
            return expense;
        }

        public void setExpense(long expense) {
            this.expense = expense;
        }
    }

    @PostMapping(value = "/findTransactions")
    public Response findTransactions(@RequestBody  Condition condition) {
        TransactionData td = new TransactionData();
        Subject currUser = SecurityUtils.getSubject();
        condition.setUserID(((User) currUser.getPrincipal()).getId());
        td.setList(userMapper.ListTransactions(condition));
        long income = 0;
        long expense = 0;
        td.setBalance(userMapper.GetProperty((User) currUser.getPrincipal()).getBalance());
        for (Transaction t: td.getList()) {
           if (t.getType() == 0) {
               income += t.getAmount();
           } else {
               expense += t.getAmount();
           }
        }
        td.setIncome(income);
        td.setExpense(expense);
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                userMapper.CountTransactions(condition), td);
    }

    @PostMapping("/logout")
    public Response logout() {
        Subject currUser = SecurityUtils.getSubject();
        currUser.logout();
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0, null);
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
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    "账户不存在");
        } catch (IncorrectCredentialsException ice) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                    "密码不正确");
        } catch (LockedAccountException lae) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                    "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                    "用户名或密码错误次数过多，请十分钟后再试");
        } catch (AuthenticationException ae) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                    "未知错误，请重试");
        }

        if (currUser.isAuthenticated()) {
            Session session = currUser.getSession();
            //下面的是自定义的代码，随你怎么写
            session.setAttribute("userInfo", currUser.getPrincipal());
            session.setAttribute("propertyInfo",
                    userMapper.GetProperty((User) currUser.getPrincipal()));
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                    0, currUser.getPrincipal());
        }
        return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0, "UserName" +
                " or password not correct");
    }

    @RequestMapping("/info")
    public Response Info(User user) {
        user.setRoles(userMapper.ListRoles(user));
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0, user);
    }

    @PostMapping("/createUser")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
            Exception.class, value = "myTransactionManager")
    public Response createUser(@RequestBody User user) throws NoSuchAlgorithmException {
        if (userMapper.GetUser(user) != null) {
            return new Response(ResponseCode.FAIL.ordinal(),"FAIL",0,
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
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,null);
        } else {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
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
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",0,
                    null);
        }
        return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                "Verification codes do not match.");
    }

    @RequestMapping("/deleteUser")
    public int deleteUser(User user) {
        return userMapper.DeleteUser(user);
    }

    @PostMapping("/createTransaction")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
            Exception.class)
    public Response createTransaction(@RequestBody Transaction transaction) throws NoSuchAlgorithmException {
        try {
            Subject currUser = SecurityUtils.getSubject();
            Property property =
                    userMapper.GetProperty((User)currUser.getPrincipal());
            long newBalance;
            transaction.setAmount(transaction.getAmount() * 100);
            if (transaction.getType() == 0) { // 充值
                newBalance = property.getBalance() + transaction.getAmount();
                transaction.setBalance(newBalance);
                property.setBalance(newBalance);
                bankMapper.subtract(transaction);
                alipayMapper.subtract(transaction);
            } else { // 提现
                newBalance = property.getBalance() - transaction.getAmount();
                transaction.setBalance(newBalance);
                property.setBalance(newBalance);
                bankMapper.increase(transaction);
                alipayMapper.increase(transaction);
            }
            userMapper.CreateTransaction(transaction);
            userMapper.UpdateProperty(property);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    userMapper.GetTransaction(transaction));
        } catch (Exception e){
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL",0,
                    e.getMessage());
        }
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
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,null);
    }
}
