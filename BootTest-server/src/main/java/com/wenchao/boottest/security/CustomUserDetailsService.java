package com.wenchao.boottest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wenchao.boottest.entity.mybatis.User;
import com.wenchao.boottest.mapper.UserMapper;




@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserMapper userDao;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
        return new SecurityUser(user);
    }
    
    public User loadUserByLoginName(String userName)  {
    	return userDao.findByName(userName);
    }
}
