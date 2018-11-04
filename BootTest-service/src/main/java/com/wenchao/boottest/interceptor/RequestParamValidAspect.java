package com.wenchao.boottest.interceptor;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wenchao.common.exception.ParameterException;


@Aspect
@Component
public class RequestParamValidAspect {
	private Logger log=LoggerFactory.getLogger(RequestParamValidAspect.class);
	
	private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final ExecutableValidator methodValidator = factory.getValidator().forExecutables();
    private final Validator beanValidator = factory.getValidator();

    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object [] params){
        return methodValidator.validateParameters(obj, method, params);
    }

    private <T> Set<ConstraintViolation<T>> validBeanParams(T bean) {
        return beanValidator.validate(bean);
    }

    @Pointcut("execution(* com.wenchao.boottest.service.*.*(..))")
    public void soaServiceBefore(){}

    /* * 通过连接点切入 */
    @Before("soaServiceBefore()")
    public void twiceAsOld1(JoinPoint point) {
        //  获得切入目标对象
        Object target = point.getThis();
        // 获得切入方法参数
        Object [] paranValues = point.getArgs();
//        for(int i=0;i<paranValues.length;i++){
//        	log.info("method check paranValues---------{}",paranValues[i] );
//        }
        MethodSignature signature = ((MethodSignature) point.getSignature());
        // 获得切入的方法
        Method method = signature.getMethod();
        //获取方法参数名
//        String[] paramNames = signature.getParameterNames();
//        for(int i=0;i<paramNames.length;i++){
//        	log.info("method check paramNames---------{}",paramNames[i] );
//        }
        // 校验以基本数据类型 为方法参数的
        Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, paranValues);
        Iterator<ConstraintViolation<Object>> violationIterator = validResult.iterator();
        while (violationIterator.hasNext()) {
            // 此处可以抛个异常提示用户参数输入格式不正确
        	String errormsg=violationIterator.next().getMessage();
//        	log.info("method errormsg---------{}",errormsg );
//        	log.info("method errormsg---------{}",violationIterator.next().getMessageTemplate() );
//        	log.info("method getLeafBean---------{}",violationIterator.next().getLeafBean() ); // 获得验证失败的类 constraintViolation.getLeafBean()
//        	log.info("method getInvalidValue---------{}",violationIterator.next().getInvalidValue());  // 获得验证失败的值 constraintViolation.getInvalidValue()
//        	log.info("method getExecutableParameters---------{}",violationIterator.next().getExecutableParameters() ); // 获取参数值 constraintViolation.getExecutableParameters()
//        	log.info("method getExecutableReturnValue---------{}",violationIterator.next().getExecutableReturnValue() );  // 获得返回值 constraintViolation.getExecutableReturnValue()
        	throw new ParameterException(errormsg);
        }

        // 校验以java bean对象 为方法参数的 
        for (Object bean : paranValues) {
            if (null != bean) {
                validResult = validBeanParams(bean);
                violationIterator = validResult.iterator();
                while (violationIterator.hasNext()) {
            // 此处可以抛个异常提示用户参数输入格式不正确
                	String errormsg=violationIterator.next().getMessage();
//                	log.info("bean check-------{}", errormsg);
                	throw new ParameterException(errormsg);
                }
            }
        }
        
        
    }
}
