package com.wenchao.boottest;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.wenchao.boottest.entity.mybatis.User;
import com.wenchao.boottest.service.BaseService;
import com.wenchao.boottest.service.TestService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMyService {
	
	private Logger log=LoggerFactory.getLogger(this.getClass());

	@Autowired
	TestService testService;
	@Autowired
	BaseService baseService;
	
	
	@Test
	public void test() {
		testService.test();
	}
	@Test
	public void one() {
		testService.one(null, null);
	}
	
	@Test
	public void findAll() {
		List<User> list=testService.findAll();
		log.info(JSON.toJSONString(list));
	}
	
	@Test
	public void insertUser() {
		User user=new User();
		user.setUserPhone("13764106514");
		user.setUsername("admin");
		user.setNickname("wenchao");
		user.setPassword("$2a$10$44.PMhjEN2Zm5vvNaQat6ub6.6tO6oxDXCY6uY3//UloAANT1OXgS");//123456
		user.setEmail("463032878@qq.com");
//		user.setCreateTime(new Date());
		baseService.insert(user);
	}
}
