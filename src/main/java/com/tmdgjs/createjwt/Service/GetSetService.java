package com.tmdgjs.createjwt.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class GetSetService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private RedisTemplate<String, Object> redisTemplateTest;

    public GetSetService(RedisTemplate<String, Object> abc){
       this.redisTemplateTest = abc;
    }

    public Object test(){
        Long count = 0L;
        final String key = "tmdgjs";
        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();

        //stringStringValueOperations.set(key,"1");
        //stringStringValueOperations.increment(key);

        stringStringValueOperations.increment(key, 1);
        stringStringValueOperations.increment(key + "123", 2);
        count = Long.valueOf((String) Objects.requireNonNull(stringStringValueOperations.get(key)));


        //stringStringValueOperations.set(key,"2");

        final Long result_2 = Long.valueOf((String) stringStringValueOperations.get(key));

        System.out.println(result_2);

        return count;
    }

    public Object inputTest( Integer i ){

        final String key = "test1";

        final ValueOperations<String, Object> str = redisTemplateTest.opsForValue();

        str.set(key, i.toString());

        return str.get(key);

    }



}
