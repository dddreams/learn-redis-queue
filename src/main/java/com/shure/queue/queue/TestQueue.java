package com.shure.queue.queue;


import com.shure.queue.queue.model.MqMessage;
import com.shure.queue.queue.producer.Producer;
import com.shure.queue.websocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/queue")
public class TestQueue {

    @Autowired
    private Producer producer;

    @GetMapping("/test")
    public String test(String name) {
        MqMessage message = new MqMessage();
        message.setTopic("test");
        message.setContent(new Message(UUID.randomUUID().toString(), "00A", name));
        producer.sendChannelMess("test", message);
        return "success";
    }

}
