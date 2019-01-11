package com.caotinging.websocket;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author caoting
 * @date 2018年9月19日
 */
@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hs")
    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息
    public void handleChat(Principal principal, Message message) {

        /**
         * 此处是一段硬编码。如果发送人是wyf 则发送给 wisely 如果发送人是wisely 就发送给 wyf。
         */
        if (principal.getName().equals("wyf")) {
            //通过convertAndSendToUser 向用户发送信息,
            // 第一个参数是接收消息的用户,第二个参数是浏览器订阅的地址,第三个参数是消息本身

            messagingTemplate.convertAndSendToUser("wisely",
                    "/queue/notifications", principal.getName() + "-send:"
                            + message.getName());
        } else {
            messagingTemplate.convertAndSendToUser("wyf",
                    "/queue/notifications", principal.getName() + "-send:"
                            + message.getName());
        }
    }
}
