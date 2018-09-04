package com.qiyuely.remex.rocketmq.exception;

import com.qiyuely.remex.exception.RemexException;

/**
 * remex rocketmq运行异常类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexMqException extends RemexException {
	private static final long serialVersionUID = 1L;

	public RemexMqException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RemexMqException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RemexMqException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RemexMqException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RemexMqException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
