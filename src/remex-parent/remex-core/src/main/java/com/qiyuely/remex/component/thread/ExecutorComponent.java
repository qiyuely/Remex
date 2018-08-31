package com.qiyuely.remex.component.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.qiyuely.remex.component.thread.interfaces.IExecutorScheduler;
import com.qiyuely.remex.exception.RemexException;

/**
 * 线程池工具组件类，提供一些快速实现的方法
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ExecutorComponent {
	
	/** 线程数量 */
	private int threadCount = 3;
	
	/**
	 * 创建一个线程池工具组件
	 * @param threadCount
	 */
	public ExecutorComponent(int threadCount) {
		if (threadCount > 0) {
			this.threadCount = threadCount;
		}
	}
	
	/**
	 * 执行
	 * @param scheduler 调度器
	 */
	public <R, T> T execute(IExecutorScheduler<R, T> scheduler) {
		try {
			int index = 0;
			//创建线程池
			ExecutorService executor =Executors.newFixedThreadPool(threadCount);
			//线程处理结果列表
			List<Future<R>> futureList = new ArrayList<Future<R>>();
			
			while (true) {
				final int curIndex = index++;
				
				//结束处理
				if (!scheduler.isContinueSchedule(curIndex)) {
					break;
				}
				
				//线程处理
				Future<R> future = executor.submit(() -> {
					R result = scheduler.schedule(curIndex);
					
					return result;
				});
				//记录处理结果
				futureList.add(future);
			}
			
			//全部处理完后最终处理
			T finishResult = scheduler.finishHandle(futureList);
			return finishResult;
			
		} catch (Exception e) {
			throw new RemexException("Executor component execute error!", e);
		}
	}
}
