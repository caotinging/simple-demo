# simple-demo

钻研技术用的各种demo

> 各项都是一个独立的模块，可以独立测试运行。

## redis-demo 

1. 实现了LOL盒子英雄热门实时排行榜的简单demo（可自行扩展成各种实时排行榜 player产生数据 view查看数据）

2. 利用redis实现一个简单的任务调度 (先执行TaskProducer生成任务，然后执行TaskConsumer消费者调用任务)

## zookeeper-demo 

1. 利用zookeeper实现分布式共享锁的简单demo

2. 利用zookeeper实现服务上下线动态感知的简单demo

## websocket-demo

1. https目录下有http连接池的demo （做了证书绕过 也可以请求https哦 不过有更好的办法 可以下载证书 有时间补充）

2. ws目录下为websocket服务端和websocket客户端的简单demo（直接运行项目即可）

## springboot-dynamic-task

[springboot动态控制定时任务](https://juejin.im/post/5cf099556fb9a07ef2010716)

> test目录下的单元测试类里进行测试哦

## springboot-yml-value

[拒绝臃肿：更优雅的获取springboot yml值](https://juejin.im/post/5cf49068e51d45105d63a4b3) 

> test目录下的单元测试类里进行测试哦

## RabbitMQ

[Springboot+RabbitMQ 快速入门](https://www.cnblogs.com/tinging/p/12605513.html)

> 源码中test目录下RabbitMQTest单元测试类有各类型交换机的测试用例