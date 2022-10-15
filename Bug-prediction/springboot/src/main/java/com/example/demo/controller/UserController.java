package com.example.demo.controller;

import com.example.demo.pojo.table.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public int register(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        @RequestParam("email")String email){

        if(userService.register(username, password, email) != 0){
            return 0;
        }

        return 1;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = userService.login(email, password);
        if (user == null)
            return "Error";

        return user.getUsername();
    }
}
