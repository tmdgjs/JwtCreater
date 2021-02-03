package com.tmdgjs.createjwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
public class RedisStringsTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testStrings(){

        final String key = "sabarada";

        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(key, "1"); // redis set 명령어
        final Object result_1 = stringStringValueOperations.get(key); // redis get 명령어

        System.out.println("result_1 = " + result_1);

        stringStringValueOperations.increment(key); // redis incr 명령어
        final Object result_2 = stringStringValueOperations.get(key);

        System.out.println("result_2 = " + result_2);

    }

}
