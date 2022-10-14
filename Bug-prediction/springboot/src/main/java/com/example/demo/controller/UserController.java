package com.example.demo.controller;

import com.example.demo.pojo.table.User;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
public class UserController {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    @ResponseBody
    public int sendCode(@RequestParam("email")String email, @RequestParam("type")int type){
        return mailService.sendCode(email, type);
    }

    @GetMapping("findcode/{email}/{type}")
    @ResponseBody
    public String findCode(@PathVariable String email, @PathVariable int type){
        return mailService.getCode(email, type);
    }

    @GetMapping("/send/{email}")
    @ResponseBody
    public int send(@PathVariable String email){
        return mailService.sendCodeREGISTER(email);
    }

    @PostMapping("/register")
    @ResponseBody
    public int register(User user, @RequestParam("code")String codeValue){

        String code = mailService.getCode(user.getEmail(), Global.REGISTER);

        if(codeValue.compareTo(code) != 0){
            return -1;
        }

        if(userService.register(user) != 0){
            return 0;
        }

        return 1;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public int login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (userService.login(username, password))
            return 0;
        return 1;
    }
}
