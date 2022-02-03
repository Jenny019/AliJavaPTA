package com.example.alijavapta.mapper;

import com.example.alijavapta.domain.*;

import java.util.List;

public interface UserMapper {
    List<User> ListUsers();
    List<CouponRecord> ListCoupons(Condition condition);
    List<Address> ListAddresses(Condition condition);
    List<Role> ListRoles(Condition condition);

    int CreateUser(User user);
    int UpdateUser(User user);
    int ResetPassword(User user);
    int DeleteUser(User user);

    User GetUser(User user);
    User Login(User user);
}
