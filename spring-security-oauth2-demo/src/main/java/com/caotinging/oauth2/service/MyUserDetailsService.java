package com.caotinging.oauth2.service;

import com.caotinging.oauth2.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: bank-door-control
 * @description: 管理员登录权限服务层
 * @author: CaoTing
 * @date: 2020/6/15
 */
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("LoginID : {}",loginId);

        List<Role> auth = new ArrayList<>();
        auth.add(new Role("超级管理员","ROLE_ADMIN"));

        //返回一个SpringSecurity需要的用户对象
        return new User("admin","$2a$10$cXbo1GwxnvXcu/O9Ci8BK.ygj72hbEgRpdipCf4viX.neXQZl0V86",auth);
    }
}
