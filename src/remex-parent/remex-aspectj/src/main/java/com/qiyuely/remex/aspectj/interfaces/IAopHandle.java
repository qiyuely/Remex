package com.qiyuely.remex.aspectj.interfaces;

import com.qiyuely.remex.aspectj.dto.AopHandleHead;
import com.qiyuely.remex.aspectj.dto.AopHandleResult;
import com.qiyuely.remex.aspectj.dto.AopHandleRunInfo;

/**
 * aop处理器
 * 
 * @author Qiaoxin.Hong
 *
 */
public interface IAopHandle {

	/**
	 * 方法执行前置处理
	 * @return
	 */
	public AopHandleResult before(AopHandleHead head, AopHandleRunInfo runInfo);
	
	/**
	 * 方法执行后置处理
	 * @return
	 */
	public AopHandleResult after(AopHandleHead head, AopHandleRunInfo runInfo, Object result);
	
	/**
	 * 方法执行异常处理
	 * @return
	 */
	public AopHandleResult exception(AopHandleHead head, AopHandleRunInfo runInfo);
	
	/**
	 * 方法执行最终处理
	 * @return
	 */
	public AopHandleResult end(AopHandleHead head, AopHandleRunInfo runInfo, Object result);
}
