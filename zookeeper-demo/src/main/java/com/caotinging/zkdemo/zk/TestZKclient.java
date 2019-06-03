package com.caotinging.zkdemo.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟zookeeper客户端
 * @author caoting
 * @date 2018年12月19日
 *
 */
public class TestZKclient {

	/**
	 * 允许一个或多个线程等待直到在其他线程中执行的一组操作完成的同步辅助。
	 */
	final static CountDownLatch countDownLatch = new CountDownLatch(1);// 计数器
	
	private ZooKeeper zk;
	
	public void connectZk() throws IOException, InterruptedException {
		zk = new ZooKeeper("localhost:2181,localhost:2182,localhost:2183", 6000, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				// 监听所有事件
				if (event.getState() == KeeperState.SyncConnected) {
					/**
					 * 减少锁存器的计数，如果计数达到零则释放所有等待的线程。
					 * 如果当前计数大于零，则递减。如果新计数为零，则重新启用所有等待线程以进行线程调度。
					 * 如果当前计数等于零，则没有任何反应。
					 */
					countDownLatch.countDown();// 开始信号
					System.out.println(event.getState());
				}
				System.out.println(event.getPath());
				System.out.println(event.getType());
				try {
					// 循环监听
					zk.getChildren("/ceshi", true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		countDownLatch.await();// 等待信号
	}
	
	public void dosomething() throws UnsupportedEncodingException, KeeperException, InterruptedException {
		/*String create = zk.create("/ceshi/ceshi1111", "ceshi111".getBytes("UTF-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(create);*/
		
		byte[] data = zk.getData("/ceshi/ceshi1111", true, null);
		System.out.println("getData:"+ new String(data,"UTF-8"));
		
		Stat setData = zk.setData("/ceshi/ceshi1111", "4444".getBytes(), -1);
		System.out.println(setData);
		
		Thread.sleep(1000);
		
		byte[] data2 = zk.getData("/ceshi/ceshi1111", true, null);
		System.out.println("getData:"+new String(data2,"UTF-8"));
	}
	
	public static void main(String[] args) {
		try {
			TestZKclient t = new TestZKclient();
			t.connectZk();
			
			try {
				// 业务处理
				t.dosomething();
				Thread.sleep(Long.MAX_VALUE);
				
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
