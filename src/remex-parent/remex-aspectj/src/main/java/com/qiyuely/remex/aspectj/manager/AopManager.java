package com.qiyuely.remex.aspectj.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import com.qiyuely.remex.aspectj.dto.AopHandleHead;
import com.qiyuely.remex.aspectj.dto.AopHandleResult;
import com.qiyuely.remex.aspectj.dto.AopHandleRunInfo;
import com.qiyuely.remex.aspectj.interfaces.IAopHandle;
import com.qiyuely.remex.core.constant.BConst;
import com.qiyuely.remex.utils.DateUtils;
import com.qiyuely.remex.utils.IdUtils;
import com.qiyuely.remex.utils.StringUtils;

/**
 * aop管理器
 * 
 * @author Qiaoxin.Hong
 *
 */
public class AopManager {

	/** aop处理器列表 */
	private List<IAopHandle> aopHandleList = new ArrayList<>();
	
	/**
	 * 环绕通知
	 * @param point
	 * @return
	 */
	public Object doAround(ProceedingJoinPoint point) {
		//结果
		Object result = null;
		//aop处理过程中，各处理的结果信息
		AopHandleRunInfo runInfo = new AopHandleRunInfo();
		//aop处理所需的请求信息
		AopHandleHead head = fetchHead(point);
		//aop处理结果
		AopHandleResult aopHandleResult = null;
		
		try {
			//方法执行前
			for (IAopHandle aopHandle : aopHandleList) {
				aopHandleResult = aopHandle.before(head, runInfo);
				runInfo.setBeforeResult(aopHandleResult);
				if (aopHandleResult != null) {
					if (!aopHandleResult.isSuccess()) {
						runInfo.setSuccess(false);
					}
					if (aopHandleResult.isReplaceResult()) {
						result = aopHandleResult.getReplaceResult();
					}
				}
			}
			
			//执行方法
			result = point.proceed();
			
			//方法执行后
			for (IAopHandle aopHandle : aopHandleList) {
				aopHandleResult = aopHandle.after(head, runInfo, result);
				runInfo.setAfterResult(aopHandleResult);
				if (aopHandleResult != null) {
					if (!aopHandleResult.isSuccess()) {
						runInfo.setSuccess(false);
					}
					if (aopHandleResult.isReplaceResult()) {
						result = aopHandleResult.getReplaceResult();
					}
				}
			}
		} catch (Throwable e) {
			runInfo.setException(e);
			runInfo.setSuccess(false);

			//方法执行异常
			for (IAopHandle aopHandle : aopHandleList) {
				aopHandleResult = aopHandle.exception(head, runInfo);
				runInfo.setExceptionResult(aopHandleResult);
				if (aopHandleResult != null) {
					if (!aopHandleResult.isSuccess()) {
						runInfo.setSuccess(false);
					}
					if (aopHandleResult.isReplaceResult()) {
						result = aopHandleResult.getReplaceResult();
					}
				}
			}
		} finally {
			//方法执行最终
			for (IAopHandle aopHandle : aopHandleList) {
				aopHandleResult = aopHandle.end(head, runInfo, result);
				runInfo.setEndResult(aopHandleResult);
				if (aopHandleResult != null) {
					if (!aopHandleResult.isSuccess()) {
						runInfo.setSuccess(false);
					}
					if (aopHandleResult.isReplaceResult()) {
						result = aopHandleResult.getReplaceResult();
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 提取aop处理所需的请求信息
	 * @param point
	 * @return
	 */
	protected AopHandleHead fetchHead(ProceedingJoinPoint point) {
		AopHandleHead head = new AopHandleHead(point);
		
		Signature signature = point.getSignature();
		head.setSignature(signature);
		
		//当前时间
		Date curDate = DateUtils.getCurDate();
		head.setCurDate(curDate);
		
		//请求的id
		String sid = IdUtils.createId();
		head.setSid(sid);
		if (signature instanceof MethodSignature) {
			//方法
			Method method = ((MethodSignature) signature).getMethod();
			head.setMethod(method);
		}
		
		//方法名
		String methodName = StringUtils.defaultString(signature.getDeclaringTypeName()) + BConst.PERIOD
				+ StringUtils.defaultString(signature.getName());
		head.setMethodName(methodName);
		
		//请求参数
		Object[] args = point.getArgs();
		head.setArgs(args);
		
		return head;
	}
	
	/**
	 * 添加aop处理器
	 * @param aopHandles
	 */
	public AopManager addAopHandle(IAopHandle...aopHandleArr) {
		for (IAopHandle aopHandle : aopHandleArr) {
			aopHandleList.add(aopHandle);
		}
		
		return this;
	}
}
