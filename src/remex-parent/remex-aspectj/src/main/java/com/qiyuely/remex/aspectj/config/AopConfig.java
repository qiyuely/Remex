package com.qiyuely.remex.aspectj.config;

import org.aspectj.lang.ProceedingJoinPoint;
import com.qiyuely.remex.aspectj.manager.AopManager;

/**
 * aop配置类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopConfig {
	
	/** aop管理类 */
	private AopManager aopManager;
	
	public AopConfig() {
		//初始化aop管理器
		aopManager = initAopManager();
		//初始化aop处理器
		initAopHandle(aopManager);
	}
	
	/**
	 * 环绕通知处理实现
	 */
	public Object doAround(ProceedingJoinPoint point) {
		return aopManager.doAround(point);
	}
	
	/**
	 * 初始化aop管理器
	 * @return
	 */
	protected AopManager initAopManager() {
		return new AopManager();
	}
	
	/**
	 * 初始化aop处理器
	 * @param aopManager
	 */
	protected void initAopHandle(AopManager aopManager) {
		
	}
}
