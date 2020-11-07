package com.shure.queue;

import com.shure.queue.redisMqPs.publish.Publish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestPs {

    @Autowired
    private Publish publish;

    @Test
    public void testps() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "tom");
        map.put("age", 20);
        publish.sendChannelMess("test", map);
    }

}
