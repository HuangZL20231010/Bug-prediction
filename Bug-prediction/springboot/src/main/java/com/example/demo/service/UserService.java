package com.example.demo.service;

import com.example.demo.pojo.table.User;

import java.util.ArrayList;

public interface UserService {

    boolean ifExistUserName(String userName);

    boolean ifExistUser(User user);

    int register(User user);

    boolean login(String username, String password);

    ArrayList<User> getRankingList();
}
