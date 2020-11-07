package com.shure.queue.websocket;

import com.shure.queue.redisMqPs.publish.Publish;
import com.shure.queue.websocket.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/socket")
public class SocketController {

    private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private Publish publish;

    @MessageMapping("/message")
    //@SendTo("/topic/message")
    public void message(Message message) {
        logger.info("message:", message);
        message.setPkId(UUID.randomUUID().toString());
        message.setMsgType("00A");
        publish.sendChannelMess("test", message);
        //return message;
    }

    @GetMapping("/test")
    public String test(String message) {
        Message m = new Message();
        m.setMsg(message);
        m.setPkId(UUID.randomUUID().toString());
        m.setMsgType("00A");
        messagingTemplate.convertAndSend("/topic/message/resis", m);
        return "test: " + message;
    }

}
