package com.bise.mqdemo.receiver;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: simple-demo
 * @description:
 * @author: CaoTing
 * @date: 2020/4/10
 */
@Data
public class User implements Serializable {

    private String username;

    private String address;

    public User(String username, String address) {
        this.username = username;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
