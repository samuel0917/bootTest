package com.wenchao.boottest.interceptor;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wenchao.boottest.entity.mybatis.Log;
import com.wenchao.boottest.entity.mybatis.User;
import com.wenchao.boottest.service.BaseService;
import com.wenchao.boottest.service.RedisService;
import com.wenchao.common.bean.CheckResult;
import com.wenchao.common.bean.Constants;
import com.wenchao.common.enums.EnumResultCode;
import com.wenchao.common.exception.ParameterException;
import com.wenchao.common.utils.CommonUtil;
import com.wenchao.common.utils.JwtUtils;
import com.wenchao.common.utils.ResponseMsgUtil;





@Aspect
@Component
public class LogAopAspect {
	 private final Logger logger = LoggerFactory.getLogger(LogAopAspect.class);
	 
	 @Autowired
	 BaseService baseService;
	 @Autowired
	 RedisService redisService;
	 
	 ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	 
	 @Pointcut("execution(public * com.wenchao.boottest.server.controller..*(..))")
	 private void webLog(){}
	 
	 /**
	     * 记录操作日志
	     */
	    @Before("webLog()")  // 使用上面定义的切入点
	    public void webLog(JoinPoint joinPoint){
	    	logger.info("webLog aop before method: {}" , joinPoint.getSignature().getName());
//	    	validToken(joinPoint);
	    	startTime.set(System.currentTimeMillis());
//	        Long start = System.currentTimeMillis();
//	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//	        HttpServletRequest request = attributes.getRequest();
//	        // 记录下请求内容
//	        logger.info("IP : {}" , request.getRemoteAddr());
//	        logger.info("ARGS :{} " , Arrays.toString(joinPoint.getArgs()));
//	        insertLog(request, joinPoint);
//	        Long end = System.currentTimeMillis();
//	        logger.info("记录日志消耗时间:{}", (end - start));
	    }
//	    @After("webLog()")
//	    public void doAfter(JoinPoint joinPoint) {
//	        logger.info("webLog aop After method: {}" , joinPoint.getSignature().getName());
//	    }
//	    @Around("webLog()")
//	    public void around(JoinPoint joinPoint) {
//	        logger.info("webLog aop Around method: {}" , joinPoint.getSignature().getName());
//	    }
	    
	    @AfterReturning(returning = "ret", pointcut = "webLog()")
	    public void doAfterReturning(JoinPoint joinPoint,Object ret) throws Throwable {
	        // 处理完请求，返回内容
	    	Long processTime=System.currentTimeMillis()-startTime.get();
	    	
	    	logger.info("webLog aop Around method: {}" , joinPoint.getSignature().getName());
	        logger.info("RESPONSE : {}" ,  ret);
	        logger.info("访问结束耗时 : {}" ,processTime);
	        
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        // 记录下请求内容
	        logger.info("log aop IP : {}" , request.getRemoteAddr());
	        logger.info("log aop ARGS :{} " , Arrays.toString(joinPoint.getArgs()));
	        
	        Long start = System.currentTimeMillis();
	        insertLog(request, joinPoint,processTime);
	        Long end = System.currentTimeMillis();
	        logger.info("记录日志消耗时间:{}", (end - start));
	    }
	    
	    @AfterThrowing(pointcut = "webLog()",throwing = "throwable")
	    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
	    	logger.info("webLog aop afterThrowing method: {}" , joinPoint.getSignature().getName());
	    	logger.info("webLog aop afterThrowing method error: {}" , throwable.getMessage());
	    }
	    
	    public void validToken(JoinPoint joinPoint){
	    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		    HttpServletRequest request = attributes.getRequest();
		    HttpServletResponse response=attributes.getResponse();
		    SecurityContext ctx = SecurityContextHolder.getContext();
		    Authentication auth = ctx.getAuthentication();
		    
		    logger.info("webLog aop request authentication.getName() : {}" ,  auth.getName());
//            if(auth.isAuthenticated()){
		    if(!"login".equals(joinPoint.getSignature().getName())){
		    	String token = request.getHeader("token");  
		        logger.info("webLog aop request token : {}" ,  token);
		        User redisUser=JwtUtils.unsign(token, User.class);
		        if(redisUser!=null){
		        	 logger.info("webLog aop request uid : {}" , redisUser.getId());
				     if(redisUser.getId()!=null){
				    	String redisUid=Constants.LOGIN_UID_PREFIX+redisUser.getId();
			        	if(redisService.hasKey(redisUid)){
			        	     String redisToken=(String) redisService.get(redisUid);
			        	     logger.info("log aop request redisToken : {}" ,  redisToken);
			        	     if(redisToken!=null&&redisToken.equals(token)){
			        	    	 //默认1个月过期， jwt先过期 redis 过期时间是jwt2倍 jwt过期就刷新token
			        	    	 CheckResult resule=JwtUtils.validateJWT(token);
			        	    	 //jwt过期
			        	    	 if(!resule.isSuccess()&&resule.getErrCode()==JwtUtils.JWT_ERRCODE_EXPIRE){
			        	    		 long redisExpireTime=JwtUtils.JWT_TTL_MONTH;
			        	             String jwtToken=JwtUtils.sign(redisUser, redisExpireTime>>2);//accesstoken
			        	             logger.info("validToken refresh jwt token:{}" ,jwtToken);
			        	             redisService.set(redisUid, jwtToken, redisExpireTime);
			        	             ResponseMsgUtil.responseWrite(jwtToken, response);
			        	    	 }
			        	     }else{
			        	    	 throw new ParameterException(EnumResultCode.INVALID_TOKEN.getMessage());//返回登陆
			        	     }
			        	}else{
			        		throw new ParameterException(EnumResultCode.INVALID_TOKEN.getMessage());//返回登陆
			        	}
			        }
		        }else{
		        	throw new ParameterException(EnumResultCode.INVALID_TOKEN.getMessage());//返回登陆
       	     	}
		    }
	    }
	    
	    public void insertLog(HttpServletRequest request,JoinPoint joinPoint,Long processTime){
	    	 try {
		        	String url= request.getRequestURL().toString();
		        	logger.info("url : {}" ,  url);
		        	logger.info("request.getMethod() : {}" ,  request.getMethod());
//		        	if(!(url.endsWith("/login")&&"GET".equals(request.getMethod()))){
		        	 //解密token
		        	String token = request.getHeader("token");  
		        	String userName=null;
		        	Long userId=null;
		        	if(token!=null&&!"".equals(token.trim())){
		        		try{
		        			User user=JwtUtils.unsign(token, User.class);
			        		if(user!=null){
			        			userId=user.getId();
			        			userName=user.getUsername();
			        		}
		        		}catch (Exception e) {
		        			logger.error("insertLog error...{}",e);
						}
		        	}
	        		Log log = new Log();
	        		log.setUserId(userId);
	        		log.setUsername(userName);
	        		log.setIp(CommonUtil.getIpAddress(request));
	 	            log.setUrl(url);
	 	            log.setProcessTime(processTime);
	 	            log.setRequestType(request.getMethod());
	 	            String classMethod=joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
	 	            log.setClassMethod(classMethod);
	 	            StringBuffer sbP = new StringBuffer();
	 	            Enumeration<String> names = request.getParameterNames();
		 	  		while (names.hasMoreElements()) {
		 	  			String n = names.nextElement();
		 	  			sbP.append(n);
		 	  			sbP.append("=");
		 	  			sbP.append(request.getParameter(n));
		 	  			sbP.append(";");
		 	  		}
		 	  		logger.info("Params : {}" ,  sbP.toString());
		 	  		if (sbP.length() > 0) {
		 				if (sbP.length() > 5000) {
		 					log.setParams(sbP.substring(0, 5000));
		 				} else {
		 					log.setParams(sbP.toString());
		 				}
		 			}
		 	  	    baseService.insert(log);
//		 	            baseService.insertResId(log);
//		 	            logger.info("webLog log id...{}",log.getId());
//		        	}
		        }catch (Exception e){
		            logger.error("插入日志异常",e);
		        }
	    }

}
