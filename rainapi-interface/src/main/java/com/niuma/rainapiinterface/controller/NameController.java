package com.niuma.rainapiinterface.controller;

import com.niuma.rainapiclientsdk.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @author niumazlb
 * @create 2022-11-09 10:55
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @PostMapping("/user")
    public String getUserByPost(@RequestBody User user, HttpServletRequest request){
        return  "Post用户叫"+user.getUsername();
    }
}
