package org.solr.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	// 让可执行程序休息一下
	private static int executePrograms = 1;
	private static int produceTaskMaxNumber = 10;

	public static void main(String[] args) {
		// 构造一个线程池
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
				2,// corePoolSize
				4,// maximumPoolSize
				3, // keepAliveTime
				TimeUnit.SECONDS,// TimeUnit
				new LinkedBlockingQueue<Runnable>(3),// BlockingQueue
				new ThreadPoolExecutor.CallerRunsPolicy()// RejectedExecutionHandler
		);

		for (int i = 1; i <= produceTaskMaxNumber; i++) {
			try {
				String task = "task@ " + i;
				System.out.println("put " + task);
				threadPool.execute(new ThreadPoolTask(task));

				// 便于观察，等待一段时间
				Thread.sleep(executePrograms);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}