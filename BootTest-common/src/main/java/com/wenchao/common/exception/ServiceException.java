package com.wenchao.common.exception;

/**
 * service层异常基类
 * 
 * @author Li Ji
 *
 */
public class ServiceException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1383424424054834749L;

	public ServiceException(int ret) {
		super(ret);
	}
   
	public ServiceException(String msg){
		super(msg);
	}
	public ServiceException(int ret, Throwable throwable) {
		super(ret, throwable);
	}

	public ServiceException(String msg, Throwable throwable){
		super(msg, throwable);
	}
	
	public ServiceException(Throwable throwable) {
		super(throwable);
	}
	
}
