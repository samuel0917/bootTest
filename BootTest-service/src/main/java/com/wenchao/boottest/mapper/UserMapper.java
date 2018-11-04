package com.wenchao.boottest.mapper;

import java.util.List;

import com.wenchao.boottest.entity.mybatis.User;


public interface UserMapper {

	User findByName(String name);
	
	List<User> findAll();
}
