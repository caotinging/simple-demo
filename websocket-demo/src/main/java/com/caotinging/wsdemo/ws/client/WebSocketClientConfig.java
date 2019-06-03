package com.caotinging.wsdemo.ws.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

@Slf4j
@Component
public class WebSocketClientConfig implements ApplicationRunner {
	
	private static Boolean isOk;
	private WebSocketClient client;
	private static WebSocketContainer conmtainer = ContainerProvider.getWebSocketContainer();

	static final String serverUrl = "ws://localhost:8084/websocket-demo/websocket/12345";
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// websocket客户端初始化  --可以拓展成多个ws客户端
		wsClientInit();
	}
	
	public void wsClientInit() {
		try {
			client = new WebSocketClient();
			conmtainer.connectToServer(client, new URI(serverUrl));
			
			isOk = true;
		} catch (Exception e) {
			isOk = false;
			e.printStackTrace();
		}

		// 自动重连的简单demo
		while (true) {
			if (isOk != null && isOk) {
				try {
				    // 心跳包的简单示例
					client.send("ping:12345");
				} catch (Exception e) {
					isOk = false;
				}
			}
			else {
				// 邮件系统连接失败进行重试
				log.warn("连接失败，正在重连...");
				try {
					client.send("ping:12345");
					log.warn("重连成功！");
					isOk = true;
				} catch (Exception e) {
					try {
						client = new WebSocketClient();
						conmtainer.connectToServer(client, new URI(serverUrl));
						
						isOk = true;
					} catch (Exception e1) {
						isOk = false;
					}
					
					if (isOk != null && isOk) {
						log.warn("重连成功！");
					}
				}
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
