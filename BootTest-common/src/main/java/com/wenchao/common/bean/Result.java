package com.wenchao.common.bean;

import java.io.Serializable;

public class Result<T> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5670874866491492616L;
	private Integer resCode;
    private String resMsg;
    private T data;


    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 转换为JSON字符串，默认:
     * WriteNullListAsEmpty    List字段如果为null,输出为[],而非null
     * WriteNullStringAsEmpty  字符类型字段如果为null,输出为”“,而非null
     * WriteMapNullValue       是否输出值为null的字段,默认为false
     */
    
    
}
