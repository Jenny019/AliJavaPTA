package com.example.alijavapta.mapper.my;

import com.example.alijavapta.domain.*;

import java.util.List;

public interface UserMapper {
    int CountUsers();
    int CountCoupons();
    int CountAddresses();
    int CountTransactions(Condition condition);

    List<User> ListUsers();
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
}
