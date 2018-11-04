package com.wenchao.common.enums;


public enum EnumResultCode {
    SUCCESS(1,"操作成功"),
    FAIL(0,"操作失败"),
    UNAUTHORIZED(401,"权限不足"),
    NOT_FOUND(404,"页面不存在"),
    INTERNAL_SERVER_ERROR(500,"系统异常"),
    USER_NOT_FOUND(10001,"用户不存在"),
    INVALID_TOKEN(10002,"token无效");

    private int code;
    
    private String message;
    

    EnumResultCode(int code) {
        this.code = code;
    }
    EnumResultCode(int code, String message) {
    	this.code = code;
		this.message = message;
	}

    public int getCode() {
        return code;
    }
    
	public String getMessage() {
		return message;
	}
    
    
}
