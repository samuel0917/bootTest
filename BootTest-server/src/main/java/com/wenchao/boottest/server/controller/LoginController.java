package com.wenchao.boottest.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	private Logger log=LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(){
    	log.info("LoginController login get start...");
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public String loginPost(){
    	log.info("LoginController loginPost start...");
        return "login";
    }
	    
    @RequestMapping("/signout")
    public String signout(HttpServletRequest request) throws Exception{
        request.logout();
        return "tologin";
    }

    @RequestMapping("/")
    public String home(){
        return "index";
    }
}
