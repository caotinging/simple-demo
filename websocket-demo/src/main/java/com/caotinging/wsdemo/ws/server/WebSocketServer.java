package com.caotinging.wsdemo.ws.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{id}")
@Component
@Slf4j
public class WebSocketServer {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

    /**
     * 当前实例使用的连接id
     */
	private String id = "";
	
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	/**
	 * 初始化方法
	 * @author caoting
	 * @date 2019年2月13日
	 */
	@PostConstruct
	public void init() {
		try {
			dosomethingInit();
		} catch (Exception e) {
			log.error("ws长连接注册id初始化错误--请检查数据库或重启服务");
		}
	}

	private void dosomethingInit() {
		// 初始化工作
		System.out.println("websocket服务已启动");
	}
	
	/**
	 * 连接建立成功时调用的方法  默认id  12345 允许建立连接
     *
     * <p>
     *     如果需要对连接的id进行管理  比如同一个id不能重复建立连接
     *     可以使用static List idList;来保存已连接的id 然后进行相关查重等操作
     * </p>
	 * @param id 
	 */
	@OnOpen
	public void onOpen(@PathParam(value = "id") String id, Session session) {
		try {
			if ("12345".equals(id)) {
				this.session = session;
				this.id = id;//接收到发送消息的人员编号

				webSocketSet.put(id, this);     //加入set中
				addOnlineCount(); // 在线数加1

				this.sendToUser("Hello:::" + id, id);
				System.out.println("用户"+id+"加入！当前在线人数为" + getOnlineCount());
			} else {
                //this.sendToUser("警告：您被禁止建立连接");

				this.session.close();
                System.out.println("有异常应用尝试与服务器进行长连接  使用id为："+id);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this.id); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		// TODO 收到客户端消息后的操作
	}

	/**
	 * 发生错误时调用 
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

    /**
     * 发送消息给当前用户
     * @param message
     * @throws IOException
     */
	public void sendMessage(String message) throws IOException {
		this.session.getAsyncRemote().sendText(message);
	}

	 /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     * @param message
     * @param sendUserId
     * @throws IOException
     */
	public Boolean sendToUser(String message, String sendUserId) throws IOException {
		Boolean flag = true;
		WebSocketServer socket = webSocketSet.get(sendUserId);
		if (socket != null) {
			try {
				if (socket.session.isOpen()) {
					socket.sendMessage(message);
				} else {
					flag = false;
				}
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
		} else {
			flag = false;
			log.warn("【" + sendUserId + "】 该用户不在线");
		}
		return flag;
	}

	/**
	 * 群发自定义消息
	 */
	public void sendToAll(String message) throws IOException {
		for (String key : webSocketSet.keySet()) {
			try {
				WebSocketServer socket = webSocketSet.get(key);
				if (socket.session.isOpen()) {
					socket.sendMessage(message);
				}
			} catch (IOException e) {
				continue;
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		if (WebSocketServer.onlineCount > 0)
			WebSocketServer.onlineCount--;
	}
}