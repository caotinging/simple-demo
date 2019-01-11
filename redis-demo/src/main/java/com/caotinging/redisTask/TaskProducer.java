package com.caotinging.redisTask;

import java.util.Random;
import java.util.UUID;

import org.junit.Test;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * 任务调度系统-任务生产者
 * @author caoting
 * @date 2018年12月21日
 *
 */
public class TaskProducer {

	// 获取一个redis的客户端连接对象
	public static Jedis getRedisConnection(String host, int port) {
		JedisShardInfo info = new JedisShardInfo(host, port);
		info.setPassword("123456");
		Jedis jedis = new Jedis(info);
		return jedis;
	}

	public static void createTask(ScheduleJob job) {
		Jedis jedis = getRedisConnection("mini1", 6379);
		Random random = new Random();
		// 生成任务
		while (true) {
			try {
				job.setBeanClass("com.caotinging.redisTask.TaskTest");
				job.setCron("0/1 * * * * ? ");
				int nextInt = new Random().nextInt(100);
				job.setJobName("ceshi"+nextInt);
				job.setJobGroup("测试组"+nextInt);
				job.setMethodName("run");
				job.setParams("haha"+nextInt);
				job.setRemarks("用于测试的任务"+nextInt);
				
				// 生成任务的速度有一定的随机性，在2-4秒之间
				Thread.sleep(random.nextInt(2000) + 2000);
				// 生成一个任务id
				String taskid = UUID.randomUUID().toString().replace("-", "");
				job.setTaskId(taskid);
				
				// 往任务队列"task-queue"中插入，第一次插入时，"task-queue"还不存在
				//但是lpush方法会在redis库中创建一条新的list数据
				jedis.lpush("task-queue", new Gson().toJson(job));
				System.out.println("TaskTest.java Id为： " + job.getTaskId()+" 任务生成");

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testCreateTask() {
		ScheduleJob job = new ScheduleJob();
		createTask(job);
	}
	
}

