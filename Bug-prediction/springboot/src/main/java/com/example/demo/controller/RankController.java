package com.example.demo.controller;

import com.example.demo.pojo.table.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/orderByAccuracy", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> getListOrderByAccuracy() {
        return userService.getRankingList();
    }
}
