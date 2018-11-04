package com.wenchao.boottest.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wenchao.boottest.entity.mybatis.User;
import com.wenchao.boottest.service.TestService;

@Controller
public class TestController {

	@Autowired
	TestService testService;
	
	@RequestMapping("/hello")
    @ResponseBody
    public String hello(){
    	List<User> list=testService.findAll();
    	
        return JSON.toJSONString(list);
    }
}
