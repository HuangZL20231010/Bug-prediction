package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.table.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean ifExistUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        return !userMapper.selectList(queryWrapper).isEmpty();
    }

    @Override
    public boolean ifExistUser(User user) {
        return ifExistUserName(user.getUsername());
    }

    @Override
    public int register(User user) {
        if (!user.getUsername().isEmpty() && !user.getPassword().isEmpty() && !user.getEmail().isEmpty() && ifExistUser(user))
            return 0;

        return userMapper.insert(user);
    }

    @Override
    public int register(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        return register(user);
    }

    @Override
    public User login(String email, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email).eq("password", password);

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public ArrayList<User> getRankingList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("maxaccuracy");
        ArrayList<User> users = new ArrayList<>();
        int i = 0;

        for (User user : userMapper.selectList(queryWrapper)) {
            if (i >= 10) break;

            users.add(user);
            i++;
        }

        return users;
    }

}
