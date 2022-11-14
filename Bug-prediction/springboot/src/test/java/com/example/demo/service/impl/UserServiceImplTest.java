package com.example.demo.service.impl;

import com.example.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)//启动类名
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void ifExistUserName() {
        assertFalse(userService.ifExistUserName("wjj"));
    }

    @Test
    public void ifExistUser() {
    }

    @Test
    public void register() {
    }

    @Test
    public void testRegister() {
    }

    @Test
    public void login() {
    }

    @Test
    public void getRankingList() {
    }
}