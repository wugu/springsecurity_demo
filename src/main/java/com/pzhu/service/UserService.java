package com.pzhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pzhu.entity.User;

public interface UserService extends IService<User> {


    void saveUserDetails(User user);
}
