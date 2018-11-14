package com.qiyuely.remex.aspectj.dto;

/**
 * aop处理过程中，各处理的结果信息
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopHandleRunInfo {
	
	/** 前置处理结果信息 */
	private AopHandleResult beforeResult;
	
	/** 后置处理结果信息 */
	private AopHandleResult afterResult;
	
	/** 异常处理结果信息 */
	private AopHandleResult exceptionResult;
	
	/** 最终处理结果信息 */
	private AopHandleResult endResult;
	
	/** 是否处理成功 */
	private boolean isSuccess = true;
	
	/** 异常信息 */
	private Throwable exception;

	public AopHandleResult getBeforeResult() {
		return beforeResult;
	}

	public void setBeforeResult(AopHandleResult beforeResult) {
		this.beforeResult = beforeResult;
	}

	public AopHandleResult getAfterResult() {
		return afterResult;
	}

	public void setAfterResult(AopHandleResult afterResult) {
		this.afterResult = afterResult;
	}

	public AopHandleResult getExceptionResult() {
		return exceptionResult;
	}

	public void setExceptionResult(AopHandleResult exceptionResult) {
		this.exceptionResult = exceptionResult;
	}

	public AopHandleResult getEndResult() {
		return endResult;
	}

	public void setEndResult(AopHandleResult endResult) {
		this.endResult = endResult;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
