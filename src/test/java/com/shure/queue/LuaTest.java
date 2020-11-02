package com.shure.queue;

import com.shure.queue.lua.LuaScriptService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LuaTest {

    private static final Logger logger = LoggerFactory.getLogger(LuaTest.class);

    @Autowired
    private LuaScriptService luaScriptService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void luaTest() {
        String highest = "highest";

        String lastestVal = "300";

        List<String> keys = new ArrayList<>();
        keys.add(highest);

        Long result = luaScriptService.execute("script/script.lua", keys, lastestVal);
        logger.info("luas:" + result);
    }

}
