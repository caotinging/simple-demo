package com.caotinging.wsdemo.ws.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 监听器类:主要任务是用ServletRequest将我们的HttpSession携带过去
 * 此注解千万千万不要忘记，它的主要作用就是将这个监听器纳入到Spring容器中进行管理,相当于注册监听
 */
@Component
@Slf4j
public class RequestListener implements ServletRequestListener {
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		// 将所有request请求都携带上httpSession
		HttpSession httpSession = ((HttpServletRequest) sre.getServletRequest()).getSession();
		log.debug("将所有request请求都携带上httpSession " + httpSession.getId());
	}

	public RequestListener() {
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
	}
}