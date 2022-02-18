package com.example.alijavapta.mapper.my;

import com.example.alijavapta.domain.*;

import java.util.List;

public interface UserMapper {
    int CountUsers(Condition condition);
    int CountCoupons(Condition condition);
    int CountAllCoupons(Condition condition);
    int CountAddresses(Condition condition);
    int CountTransactions(Condition condition);

    List<User> ListUsers(Condition condition);
    List<Coupon> ListAllCoupons(Condition condition);
    List<CouponRecord> ListCoupons(Condition condition);
    List<Address> ListAddresses(Condition condition);
    List<Role> ListRoles(User user);
    List<Permission> ListPermissions(Role role);
    List<Transaction> ListTransactions(Condition condition);

    int CreateUser(User user);
    int UpdateUser(User user);
    int ResetPassword(User user);
    int DeleteUser(User user);

    User GetUser(User user);

    int CreateProperty(User user);
    Property GetProperty(User user);
    int UpdateProperty(Property property);

    int CreateTransaction(Transaction transaction);
    Transaction GetTransaction(Transaction transaction);

    int CreateCoupon(Coupon coupon);
    int UpdateCoupon(Coupon coupon);
    int DeleteCoupon(Coupon coupon);
}
