package com.qiyuely.remex.aspectj.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * aop处理所需的请求信息
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopHandleHead implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 请求相关信息 */
	private ProceedingJoinPoint point;
	
	/** 请求相关信息 */
	private Signature signature;
	
	/** 方法 */
	private Method method;
	
	/** 方法名 */
	private String methodName;
	
	/** 请求参数 */
	private Object[] args;
	
	/** 请求的id */
	private String sid;
	
	/** 当前时间 */
	private Date curDate;
	
	public AopHandleHead() {
		super();
	}
	
	public AopHandleHead(ProceedingJoinPoint point) {
		super();
		this.point = point;
	}

	public ProceedingJoinPoint getPoint() {
		return point;
	}

	public void setPoint(ProceedingJoinPoint point) {
		this.point = point;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}
	
	public Date getCurDate() {
		return curDate;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	public Object[] getArgs() {
		return args;
	}
}
