package com.caotinging.taskdemo.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @program: simple-demo
 * @description: 定时任务控制类
 * @author: CaoTing
 * @date: 2019/5/23
 **/
public final class ScheduledTask {

    public volatile ScheduledFuture<?> future;
    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
