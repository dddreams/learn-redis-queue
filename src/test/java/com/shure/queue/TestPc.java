package com.shure.queue;

import com.shure.queue.redisMqPc.producer.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestPc {

    @Autowired
    private Producer producer;

    @Test
    public void testpc() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "tom");
        map.put("age", 20);
        producer.sendMsg("test", map);
    }

}
