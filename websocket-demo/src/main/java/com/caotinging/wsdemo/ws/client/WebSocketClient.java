package com.caotinging.wsdemo.ws.client;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.io.IOException;

/**
 * @author caoting
 * @date 2018年9月27日
 */
@Slf4j
@ClientEndpoint
public class WebSocketClient {

	private Session session;

	@OnOpen
	public void open(Session session) {
		System.out.println("连接开启...");
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println("来自系统的消息: " + message);

		// TODO 收到服务端消息后的处理
	}

	@OnClose
	public void onClose() {
		System.out.println("长连接关闭...");
	}

	@OnError
	public void onError(Session session, Throwable t) {
		t.printStackTrace();
	}

	/**
	 * 向服务端推送消息
	 * @param message
	 */
	public void send(String message) {
		this.session.getAsyncRemote().sendText(message);
	}

	public void close() throws IOException {
		if (this.session.isOpen()) {
			this.session.close();
		}
	}
}
