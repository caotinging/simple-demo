package com.caotinging.redisdemo.redisTask;

import java.io.Serializable;

/**
 * 任务调度
 */
public class ScheduleJob implements Serializable {

	private static final long serialVersionUID = 1L;

	private String taskId;// 任务id
	
	private String jobName;// 任务名称

	private String jobGroup;// 任务分组

	private String cron;// Cron表达式

	private String beanClass;// 执行类

	private String methodName;// 执行方法

	private String params;// 初始化参数

	private String remarks;// 备注

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "ScheduleJob [taskId=" + taskId + ", jobName=" + jobName + ", cron=" + cron + ", beanClass=" + beanClass + ", methodName=" + methodName + ", remarks=" + remarks + "]";
	}
	
}
