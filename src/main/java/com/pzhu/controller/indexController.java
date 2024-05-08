package com.pzhu.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class indexController {
    @GetMapping("/")
    public Map index(){

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();//获取用户身份信息
        Object credentials = authentication.getCredentials();//获取用户的凭证信息
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//获取用户的权限信息

        String name = authentication.getName();

        HashMap result = new HashMap();
        result.put("username",name);
        result.put("authentication",authentication);

        return result;
    }
}
