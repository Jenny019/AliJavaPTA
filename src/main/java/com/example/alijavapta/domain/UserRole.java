package com.example.alijavapta.domain;

import java.util.List;

public class UserRole {
    private long usersRolesID;
    private List<User> users;
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public long getUsersRolesID() {
        return usersRolesID;
    }

    public void setUsersRolesID(long usersRolesID) {
        this.usersRolesID = usersRolesID;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
