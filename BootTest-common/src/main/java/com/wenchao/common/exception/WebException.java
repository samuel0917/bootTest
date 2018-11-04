package com.wenchao.common.exception;

/**
 * web层异常基类
 * 
 * @author Li Ji
 *
 */
public class WebException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1584659508083437994L;

	public WebException(int ret) {
		super(ret);
	}

	public WebException(int ret, Throwable throwable) {
		super(ret, throwable);
	}

	

	public WebException(Throwable throwable) {
		super(throwable);
	}
}
