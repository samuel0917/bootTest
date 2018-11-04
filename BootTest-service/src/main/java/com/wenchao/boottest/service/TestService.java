package com.wenchao.boottest.service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wenchao.boottest.common.bean.ParamsVo;
import com.wenchao.boottest.entity.mybatis.User;

public interface TestService {

	public void test();
	
	void one(@NotNull(message = "不能为null") Integer a, @NotBlank(message = "不能为空") String b);
    void two(@NotNull(message = "paramsVo不能为null") ParamsVo paramsVo,
             @NotNull(message = "go不能为null") String go);
    
    public List<User> findAll();
}
