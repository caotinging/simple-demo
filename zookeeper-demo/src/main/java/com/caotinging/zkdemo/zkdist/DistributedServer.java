package com.caotinging.zkdemo.zkdist;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 服务上下线感知服务端
 * @author caoting
 * @date 2018年12月20日
 *
 */
public class DistributedServer {
	private static final String CONNECT_STRING = "localhost:2181,localhost:2182,localhost:2183";
	private static final int SESSION_TIME_OUT = 2000;
	private static final String PARENT_NODE = "/servers";

	private ZooKeeper zk = null;
	CountDownLatch latch = new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception {
		// 创建一个zookeeper连接
		DistributedServer server = new DistributedServer();
		server.getZkConnection();
		server.createServerNode(args[0]);
		server.dosomething(args[0]);
	}
	
	/**
	 * 业务逻辑
	 * @param hostname
	 * 
	 * @author caoting
	 * @throws InterruptedException 
	 * @date 2018年12月20日
	 */
	public void dosomething(String hostname) throws InterruptedException {
		System.out.println(hostname + " start working...");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	/**
	 * 创建服务节点
	 * @param hostname
	 * 
	 * @author caoting
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @date 2018年12月20日
	 */
	public void createServerNode(String hostname) throws KeeperException, InterruptedException {
		// 首先判断是否存在父节点
		Stat exists = zk.exists(PARENT_NODE, false);
		if (null == exists) {
			// 不存在则创建
			zk.create(PARENT_NODE, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		// 创建本服务路径
		/*
		 * 在客户端断开连接时将删除znode，并且其名称将附加一个单调递增的数字。(CreateMode.EPHEMERAL_SEQUENTIAL)。即：server00000001之类的
		 */
		String path = zk.create(PARENT_NODE+"/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname+" is online...path is "+ path);
	}
	
	/**
	 *  获取zk连接
	 * @author caoting
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @date 2018年12月20日
	 */
	public void getZkConnection() throws IOException, InterruptedException {
		zk = new ZooKeeper(CONNECT_STRING, SESSION_TIME_OUT, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				// 监听程序（服务端监听）
				if (KeeperState.SyncConnected == event.getState()) {
					latch.countDown();
				}
			}
		});
		latch.await();
	}

}
