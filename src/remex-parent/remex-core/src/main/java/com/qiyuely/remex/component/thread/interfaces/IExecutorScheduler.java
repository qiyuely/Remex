package com.qiyuely.remex.component.thread.interfaces;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 线程池工具组件调度器
 * 
 * @author Qiaoxin.Hong
 *
 * @param <R> 处理的返回结果
 * @param <T> 可选，全部处理完后最终处理
 */
public interface IExecutorScheduler<R, T> {

	/**
	 * 调度处理
	 * @param index 执行次数
	 * @return
	 */
	public R schedule(int index);
	
	/**
	 * 是否继续处理
	 * @param index 执行次数
	 * @return true：继续处理；false：结束
	 */
	public boolean isContinueSchedule(int index);
	
	/**
	 * 全部处理完后最终处理，可的使用Future.get()，保证所有线程都执行完，才继续走主线程
	 */
	public default T finishHandle(List<Future<R>> futureList) throws InterruptedException, ExecutionException {
		
		return null;
	}
}
