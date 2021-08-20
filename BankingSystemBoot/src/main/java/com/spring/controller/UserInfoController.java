package com.spring.controller;

import com.spring.mapper.UserInfoMapper;
import com.spring.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserInfoController {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping( value = "/newUser",produces = "application/json")
    public UserInfo newUser(@RequestBody UserInfo userInfo){
        System.out.println(userInfo.toString());
        return userInfoMapper.newUser(userInfo);
    }
    @PostMapping(value = "/login")
    public String userLogin(@RequestParam int id, @RequestParam int password){
        if(userInfoMapper.isUserExist(id,password)){
            return "Success";
        }
        return "Failed";    
    }
    @GetMapping(value = "/getCustomerInfo/{id}",produces = "application/json")
    public UserInfo getCustomerInfo(@PathVariable int id){
        return userInfoMapper.getUserInfo(id);
    }
}
