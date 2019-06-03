package com.caotinging.zkdemo.zkdist;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务上下线动态感知实现客户端
 * @author caoting
 * @date 2018年12月20日
 *
 */
public class DistributedClient {

	private static final String connectString = "localhost:2181,localhost:2182,localhost:2183";
	private static final int sessionTimeout = 2000;
	private static final String parentNode = "/servers";
	// 注意:加volatile的意义何在？
	/**
	 * Java语言提供了一种稍弱的同步机制，即volatile变量，用来确保将变量的更新操作通知到其他线程。当把变量声明为volatile类型后，
	 * 编译器与运行时都会注意到这个变量是共享的，因此不会将该变量上的操作与其他内存操作一起重排序。
	 * volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值。
	 * 在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比sychronized关键字更轻量级的同步机制。
	 */
	private volatile List<String> serverList;
	private ZooKeeper zk = null;

	public static void main(String[] args) throws Exception {
		// 获取zookeeper连接
		DistributedClient client = new DistributedClient();
		client.getZkConnection();
		//client.getServerList();
		client.dosomething();
	}

	/*
	 * 业务逻辑
	 */
	public void dosomething() throws InterruptedException {
		System.out.println("client start working...");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	public void getZkConnection() throws IOException {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				// 客户端监听程序-处理逻辑-循环监听
				try {
					getServerList();
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	/**
	 * 获取服务列表
	 * 
	 * @author caoting
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @date 2018年12月20日
	 */
	public void getServerList() throws KeeperException, InterruptedException {
		// 监听服务节点子节点变化，并重新建立一次监听
		List<String> children = zk.getChildren(parentNode, true);
		
		// 创建一个局部服务列表暂存服务器列表信息
		List<String> servers = new ArrayList<String>();
		for (String child : children) {
			// 获取子节点的数据信息
			byte[] data = zk.getData(parentNode+"/"+child, false, null);
			servers.add(new String(data));
		}
		
		// 更新serverList成员变量，提供给各业务线程使用、
		serverList = servers;
		// 打印服务器列表
		System.out.println(serverList);
	}
	
	
}
