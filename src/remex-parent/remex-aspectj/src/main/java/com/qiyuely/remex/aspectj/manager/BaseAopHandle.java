package com.qiyuely.remex.aspectj.manager;

import com.qiyuely.remex.aspectj.dto.AopHandleHead;
import com.qiyuely.remex.aspectj.dto.AopHandleResult;
import com.qiyuely.remex.aspectj.dto.AopHandleRunInfo;
import com.qiyuely.remex.aspectj.interfaces.IAopHandle;

/**
 * aop base 处理器
 * 
 * @author Qiaoxin.Hong
 *
 */
public class BaseAopHandle implements IAopHandle {

	/**
	 * 方法执行前置处理
	 * @return
	 */
	@Override
	public AopHandleResult before(AopHandleHead head, AopHandleRunInfo runInfo) {
		return createDefaultResult();
	}
	
	/**
	 * 方法执行后置处理
	 * @return
	 */
	@Override
	public AopHandleResult after(AopHandleHead head, AopHandleRunInfo runInfo, Object result) {
		return createDefaultResult();
	}
	
	/**
	 * 方法执行异常处理
	 * @return
	 */
	@Override
	public AopHandleResult exception(AopHandleHead head, AopHandleRunInfo runInfo) {
		return createDefaultResult();
	}
	
	/**
	 * 方法执行最终处理
	 * @return
	 */
	@Override
	public AopHandleResult end(AopHandleHead head, AopHandleRunInfo runInfo, Object result) {
		return createDefaultResult();
	}
	
	/**
	 * 创建默认的方法执行前置处理结果信息
	 * @return
	 */
	protected AopHandleResult createDefaultResult() {
		return new AopHandleResult();
	}
}
