package com.wenchao.boottest.security;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
/**
 * 内存内置用户 用户名密码登陆
 * @author Administrator
 *
 */
@Component
public class InMemoryAuthenticationProvider implements AuthenticationProvider{
	
	private Logger log=LoggerFactory.getLogger(this.getClass());
	
	private final String adminName = "root";
	private final String adminPassword = "root";

	//根用户拥有全部的权限
    private final List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("CAN_SEARCH"),
            new SimpleGrantedAuthority("CAN_SEARCH"),
            new SimpleGrantedAuthority("CAN_EXPORT"),
            new SimpleGrantedAuthority("CAN_IMPORT"),
            new SimpleGrantedAuthority("CAN_BORROW"),
            new SimpleGrantedAuthority("CAN_RETURN"),
            new SimpleGrantedAuthority("CAN_REPAIR"),
            new SimpleGrantedAuthority("CAN_DISCARD"),
            new SimpleGrantedAuthority("CAN_EMPOWERMENT"),
            new SimpleGrantedAuthority("CAN_BREED"));
    
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("use InMemoryAuthenticationProvider authenticate start... authentication name... {}",authentication.getName());
		boolean isMatch=isMatch(authentication);
		log.info("use InMemoryAuthenticationProvider authenticate complete... isMatch... {}",isMatch);
		if(isMatch){
            User user = new User(authentication.getName(),authentication.getCredentials().toString(),authorities);
//            return new UsernamePasswordAuthenticationToken(user,authentication.getCredentials(),authorities);
            return new RememberMeAuthenticationToken(authentication.getName(), user, authorities);
        }
        return null;
        //如果AuthenticationProvider返回了null，
//        AuthenticationManager会交给下一个支持authentication类型的AuthenticationProvider处理
	}

	public boolean supports(Class<?> authentication) {
		//support方法检查authentication的类型是不是这个AuthenticationProvider支持的，
		//这里简单地返回true，就是所有都支持，这里所说的authentication为什么会有多个类型，
//		是因为多个AuthenticationProvider可以返回不同的Authentication
//		return true;
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private boolean isMatch(Authentication authentication){
        if(authentication.getName().equals(adminName)&&authentication.getCredentials().equals(adminPassword))
            return true;
        else
            return false;
    }
}
