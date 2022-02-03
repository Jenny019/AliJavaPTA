package com.example.alijavapta.domain;

import java.util.Date;
import java.util.List;

public class UserRole {
    private String usersRolesID;
    private List<User> users;
    private List<Role> roles;
    private Date createdAt;
    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsersRolesID() {
        return usersRolesID;
    }

    public void setUsersRolesID(String usersRolesID) {
        this.usersRolesID = usersRolesID;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
