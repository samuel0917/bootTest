package com.wenchao.common.exception;

/**
 * 基础异常类 定义返回代码及异常描述
 * 
 * @author Li Ji
 *
 */
public class BaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4697818121232016727L;

	protected int ret;
	 /**
     * 异常信息
     */
    protected String msg;

	public BaseException() {
		super();
	}

	public BaseException(int ret, Throwable throwable) {
		super(throwable);
		this.ret = ret;
	}
	
	public BaseException(String msg, Throwable throwable) {
		 super(msg, throwable);
		 this.msg = msg;
	}
	
	public BaseException(int ret, String msg) {
		this.ret = ret;
		this.msg = msg;
	}


	public BaseException(int ret) {
		super(String.valueOf(ret));
		this.ret = ret;
	}
	public BaseException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BaseException(Throwable throwable) {
		super(throwable);
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public int getRet() {
		return ret;
	}

	
}
