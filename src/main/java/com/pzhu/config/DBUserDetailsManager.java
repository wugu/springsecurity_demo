package com.pzhu.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzhu.entity.User;
import com.pzhu.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {
    @Resource
    private UserMapper userMapper;
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    /**
     * 在数据库中插入新的用户信息
     * @param userDetails
     */
    public void createUser(UserDetails userDetails) {

        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(true);
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    /**
     * 从数据库中获取用户数据
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);//得到数据库中的username，第一个参数要和数据库中的一致
        User user = userMapper.selectOne(queryWrapper);//mybatisplus中得到数据库中的数据
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            /*Collection<GrantedAuthority> authorities = new ArrayList<>();
            //authorities.add(()->"USER_LIST");
            authorities.add(()->"USER_ADD");
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEnabled(),
                    true,//用户账号是否过期
                    true,//用户凭证是否过期
                    true,//用户是否未被锁定
                    authorities//权限列表
            );*/
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .disabled(!user.getEnabled())
                    .credentialsExpired(false)//是否过期
                    .accountLocked(false)
                    .roles("ADMIN")
                    .authorities("USER_ADD","USER_LIST")//添加权限字符串
                    .build();
        }


    }
}
