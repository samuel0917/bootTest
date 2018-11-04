package com.wenchao.boottest;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wenchao.boottest.service.RedisService;
import com.wenchao.boottest.service.TestService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisService {

	private Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RedisService redisService;
	
	
	@Test
	public void test() {
		
		redisService.set("test", "12345");
		log.info("test expire...{}",redisService.getExpire("test"));
		log.info("test value...{}",redisService.get("test"));
	}
	
	
}
