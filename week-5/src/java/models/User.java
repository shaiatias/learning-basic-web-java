/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import repositories.Entity;


public class User implements Entity<String> {

    public String username;
    public String password;
    public Long lastLoginTime;
    public String role;
    public String email;
    public String studentId;

    @Override
    public String getIdValue() {
        return this.username;
    }

    @Override
    public String getIdKey() {
        return "username";
    }

    public User(String username, String password, Long lastLoginTime, String role, String email, String studentId) {
        this.username = username;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.role = role;
        this.email = email;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", lastLoginTime=" + lastLoginTime + ", role=" + role + ", email=" + email + ", studentId=" + studentId + '}';
    }
}
