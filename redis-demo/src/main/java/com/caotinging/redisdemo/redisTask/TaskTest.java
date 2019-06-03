package com.caotinging.redisdemo.redisTask;

public class TaskTest {

	public void run(String test) throws InterruptedException {
		System.out.println("==================TaskTest任务开始执行... 参数：" + test);
		Thread.sleep(20*1000);
		System.out.println("TaskTest任务执行完毕...==============================");
	}
}
