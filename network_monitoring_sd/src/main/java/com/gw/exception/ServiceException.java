package com.gw.exception;

/**
 * 
 * @author  作者  lxy
 * @date    创建时间  2018年3月21日 下午5:53:37  
 * @description  自定义异常
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -2608373317729107675L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
