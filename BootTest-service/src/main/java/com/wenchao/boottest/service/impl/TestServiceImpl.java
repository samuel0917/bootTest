package com.wenchao.boottest.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wenchao.boottest.common.bean.ParamsVo;
import com.wenchao.boottest.entity.mybatis.User;
import com.wenchao.boottest.mapper.UserMapper;
import com.wenchao.boottest.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	
	private Logger log=LoggerFactory.getLogger(TestServiceImpl.class);
	
	@Value("${mns.saveLocQueue}")
	private  String saveLocQueue;
	
	@Autowired
	UserMapper userDao;
	
	public void test(){
		log.info("sendMessageQueue start...");
		log.info("sendMessageQueue...{}",saveLocQueue);
	}

	public void one(Integer a, String b) {
		// TODO Auto-generated method stub
		
	}

	public void two(ParamsVo paramsVo, String go) {
		// TODO Auto-generated method stub
		
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

}
