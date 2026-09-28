package com.URL.Shortener.url.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void test(){
//        redisTemplate.opsForValue().set("email", "vimal@gmail.com");
        Object o = redisTemplate.opsForValue().get("email");
        Object name = redisTemplate.opsForValue().get("name");
        Object age = redisTemplate.opsForValue().get("age");
        System.out.println(age.toString());
        System.out.println(name.toString());
        System.out.println(o.toString());
        int a = 1;
    }
}
