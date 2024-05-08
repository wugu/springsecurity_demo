package com.pzhu.controller;

import com.pzhu.entity.User;
import com.pzhu.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') and authentication.name == 'admin'")//角色是admin，用户名是admin才可以访问这个方法
    public List<User> getList(){
        return userService.list();
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAuthority('USER_ADD')")
    public void add(@RequestBody User user){
        userService.saveUserDetails(user);
    }
}
