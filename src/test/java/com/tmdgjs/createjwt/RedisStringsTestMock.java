package com.tmdgjs.createjwt;

import com.tmdgjs.createjwt.Service.GetSetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedisStringsTestMock {


    @Test
    public void inputTestingWithRedisStrings(){
        // given
        RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);

        GetSetService userService = new GetSetService(redisTemplate);
        Integer  i = 1;

        //when
        Object stringsValue = userService.inputTest(i);

        //then
        assertEquals(i.toString(),stringsValue);
        System.out.println(i);

    }


}
