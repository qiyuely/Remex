package com.qiyuely.remex.aspectj.dto;

import java.io.Serializable;

/**
 * aop处理结果
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopHandleResult implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 是否处理成功 */
	private boolean isSuccess = true;
	
	/** 是否替换原产生的结果集 */
	private boolean isReplaceResult = false;
	
	/** 在需要时，可以通过此值来替换原产生的结果集 */
	private Object replaceResult;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public void setReplaceResult(Object replaceResult) {
		this.isReplaceResult = true;
		this.replaceResult = replaceResult;
	}
	
	public Object getReplaceResult() {
		return replaceResult;
	}
	
	public boolean isReplaceResult() {
		return isReplaceResult;
	}
}
