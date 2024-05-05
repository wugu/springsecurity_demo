package com.pzhu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzhu.entity.User;
import com.pzhu.mapper.UserMapper;
import com.pzhu.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
