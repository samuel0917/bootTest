package com.wenchao.boottest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wenchao.boottest.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	
	private Logger log=LoggerFactory.getLogger(TestServiceImpl.class);
	
	@Value("${mns.saveLocQueue}")
	private  String saveLocQueue;
	
	public void test(){
		log.info("sendMessageQueue start...");
		log.info("sendMessageQueue...{}",saveLocQueue);
	}

}
