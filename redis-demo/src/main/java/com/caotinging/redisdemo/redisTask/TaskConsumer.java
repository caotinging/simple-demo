package com.caotinging.redisdemo.redisTask;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 任务调度执行任务方-消费方
 * 
 * @author caoting
 * @date 2018年12月21日
 *
 */
public class TaskConsumer {

	public static void main(String[] args) {
		JedisShardInfo info = new JedisShardInfo("localhost", 6379);
		info.setPassword("123456");
		Jedis jedis = new Jedis(info);

		while (true) {
			try {
				// 从task-queue中取一个任务，同时放入"tmp-queue"
				String taskJson = jedis.rpoplpush("task-queue", "tmp-queue");

				Gson gson = new Gson();
				ScheduleJob job = gson.fromJson(taskJson, ScheduleJob.class);

				// 处理任务
				Thread.sleep(3000);
				Boolean isSuccess = invokeMethod(job);

				if (!isSuccess) {
					// 失败的情况下，需要将任务从"tmp-queue"弹回"task-queue"
					jedis.rpoplpush("tmp-queue", "task-queue");
					System.out.println("-------任务处理失败： " + job);

				} else { 
					// 成功的情况下，将任务从"tmp-queue"清除
					jedis.rpop("tmp-queue");
					System.out.println("任务处理成功： " + job);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 反射调用方法
	 * @param job
	 * 
	 * @author caoting
	 * @date 2018年12月24日
	 */
	public static Boolean invokeMethod(ScheduleJob job) {
		Boolean flag = false;
		Class<?> clazz = null;
		Object obj = null;

		String beanClass = job.getBeanClass();
		String methodName = job.getMethodName();
		String params = job.getParams();

		try {
			if (StringUtils.isNotEmpty(beanClass)) {
				clazz = Class.forName(beanClass);
				if (null != clazz) {
					obj = clazz.newInstance();
					if (StringUtils.isNotEmpty(methodName)) {
						if (StringUtils.isNotEmpty(params)) {
							// 有参数方法反射调用
							Method method = clazz.getDeclaredMethod(methodName, String.class);
							method.invoke(obj, params);
							flag = true;
						} else {
							// 无参数方法反射调用
							Method method = clazz.getDeclaredMethod(methodName);
							method.invoke(obj);
							flag = true;
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
