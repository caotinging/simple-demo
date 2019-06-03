package com.caotinging.wsdemo.ws.server.cfg;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.session.StandardSessionFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

@Configuration
@Slf4j
public class WebSocketServerConfig extends Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		/* 如果没有监听器,那么这里获取到的HttpSession是null */
		StandardSessionFacade ssf = (StandardSessionFacade) request.getHttpSession();
		if (ssf != null) {
			HttpSession httpSession = (HttpSession) request.getHttpSession();
			// 关键操作
			sec.getUserProperties().put("sessionId", httpSession.getId());
			log.debug("获取到的SessionID：" + httpSession.getId());
		}
	}

	/**
	 * 如果使用独立的servlet容器，而不是直接使用springboot的内置容器
	 * 就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
	 * 即：生产环境中在独立的tomcat运行时请注释掉这个bean
	 * 
	 * @return
	 * 
	 * @author caoting
	 * @date 2019年2月20日
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}