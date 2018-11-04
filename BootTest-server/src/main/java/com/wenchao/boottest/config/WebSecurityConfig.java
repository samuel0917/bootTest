package com.wenchao.boottest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import com.alibaba.fastjson.JSON;
import com.wenchao.boottest.entity.mybatis.User;
import com.wenchao.boottest.security.AuthCodeAuthenticationProvider;
import com.wenchao.boottest.security.CustomUserDetailsService;
import com.wenchao.boottest.security.InMemoryAuthenticationProvider;
import com.wenchao.boottest.security.JdbcAuthenticationProvider;
import com.wenchao.boottest.service.RedisService;
import com.wenchao.common.bean.Constants;
import com.wenchao.common.bean.Result;
import com.wenchao.common.enums.EnumResultCode;
import com.wenchao.common.utils.JwtUtils;
import com.wenchao.common.utils.ResponseMsgUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
//@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    InMemoryAuthenticationProvider inMemoryAuthenticationProvider;
    @Autowired
    JdbcAuthenticationProvider jdbcAuthenticationProvider;
    @Autowired
    AuthCodeAuthenticationProvider authCodeAuthenticationProvider;
    @Autowired 
    @Qualifier("dataSource")
    DataSource dataSource;
    @Autowired
    RedisService redisService;
    
    @Autowired
    CustomUserDetailsService customUserDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//          auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder()); 
//       
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").permitAll()//登录页面用户任意访问
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .and().authorizeRequests()
		        .antMatchers("/images/**",
		       		 "/scripts/**",
		       		 "/styles/**").permitAll()
		        .antMatchers("/api/**").permitAll()//api开头的都能访问
		        .antMatchers("/old/**").permitAll()//老系统中的以old开头的接口都能访问
                .antMatchers("/admin/**","/reg").hasRole("超级管理员")///admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
                .anyRequest().authenticated()//其他的路径都是登录后即可访问
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
//                .and().exceptionHandling().accessDeniedPage("/deny")
                .and().logout().logoutUrl("/logout").permitAll()//设置使用默认的退出 注销行为任意访问
               
                // 自动登录
                .and().rememberMe()
                .tokenRepository(tokenRepository())//TokenRepository，登录成功后往数据库存token的
                .tokenValiditySeconds(86400)//记住我秒数
//                .userDetailsService(userDetailsService) //记住我成功后，调用userDetailsService查询用户信息
                // 关闭CSRF跨域
                .and().csrf().disable().exceptionHandling();//http.csrf().ignoringAntMatchers("/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html","/static/**");
    }

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
        jtr.setDataSource(dataSource);
        return jtr;
    }
	
	public AuthenticationSuccessHandler loginSuccessHandler(){
		return new AuthenticationSuccessHandler() {
        	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        		String jsonString=JSON.toJSONString(authentication.getPrincipal());
//        		log.info("authentication.getPrincipal() jsonString:{}" ,jsonString);
//            	JSONObject jsonObject = JSON.parseObject(jsonString);
//            	String userName=(String) jsonObject.get("username");
//            	String userId=(String) jsonObject.get("id");
        		Result result=loginSuccessResponse(authentication.getName());
//        		response.setContentType("application/json;charset=utf-8");
//        		response.setHeader("Access-Control-Expose-Headers", "Authorization");
//        		response.setHeader("Cache-Control", "no-store");
//	            PrintWriter out = response.getWriter();
////	            out.write("{\"status\":\"success\",\"msg\":\"登录成功\"}");
	            String resultStr=JSON.toJSONString(result);
//	            log.info("login resultStr:{}",resultStr);
//	            out.write(resultStr);
//	            out.flush();
//	            out.close();
        		
	            ResponseMsgUtil.responseWrite(resultStr, response);
        	}
		};
	}
	
	public Result loginSuccessResponse(String loginName){
		Result result=null;
		User user=customUserDetailsService.loadUserByLoginName(loginName);
		if(user!=null){
			long redisExpireTime=JwtUtils.JWT_TTL_MONTH;
            String jwtToken=JwtUtils.sign(user, redisExpireTime>>2);//accesstoken
            log.info("loginSuccessResponse jwt token:{}" ,jwtToken);
            String redisUid=Constants.LOGIN_UID_PREFIX+user.getId();
            redisService.set(redisUid, jwtToken, redisExpireTime);
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("token", jwtToken);
            map.put("userInfo", user);
            result=ResponseMsgUtil.responseSuccess(map);
		}else{
			result=ResponseMsgUtil.responseFail(EnumResultCode.USER_NOT_FOUND);
		}
		return result;
	}
	
	public AuthenticationFailureHandler loginFailureHandler(){
		return new AuthenticationFailureHandler() {
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            	response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write("{\"status\":\"error\",\"msg\":\"登录失败\"}");
                out.flush();
                out.close();
            }
        };
	}
	 
	@Override
    protected AuthenticationManager authenticationManager() throws Exception {
    	 List<AuthenticationProvider> providers=new ArrayList<AuthenticationProvider>();
    	 providers.add(inMemoryAuthenticationProvider);
    	 providers.add(jdbcAuthenticationProvider);
    	 providers.add(authCodeAuthenticationProvider);
//	         ProviderManager authenticationManager = new ProviderManager(Arrays.asList(inMemoryAuthenticationProvider,daoAuthenticationProvider()));
    	 ProviderManager authenticationManager = new ProviderManager(providers);
    	 //不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
         authenticationManager.setEraseCredentialsAfterAuthentication(false);
         return authenticationManager;
     }
}