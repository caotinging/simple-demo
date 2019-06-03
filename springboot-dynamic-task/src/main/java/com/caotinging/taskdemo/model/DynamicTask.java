package com.caotinging.taskdemo.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * @program: simple-demo
 * @description: 动态定时任务model
 * @author: CaoTing
 * @date: 2019/5/23
 **/
@TableName("t_task")
@Data
public class DynamicTask {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("cron")
    private String cron;
}
