package com.example.alijavapta.shiro;

import com.example.alijavapta.domain.Permission;
import com.example.alijavapta.domain.Role;
import com.example.alijavapta.domain.User;
import com.example.alijavapta.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Resource
    private UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        List<Role> roles = userMapper.ListRoles(user);
        for (Role role : roles) {
            authorizationInfo.addRole(role.getName());
            List<Permission> permissions =
                    userMapper.ListPermissions(role);
            for (Permission p : permissions) {
                authorizationInfo.addStringPermission(p.getName());
            }
        }
        return authorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = new User();
        user.setUserName(username);
        user = userMapper.GetUser(user);
        if (user == null) {
            throw new UnknownAccountException("Account does not exist.");
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(),
                getName());
    }
}