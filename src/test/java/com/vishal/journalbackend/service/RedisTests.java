package com.vishal.journalbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisTests {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisTests(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Test
    void testRedisTemplate() {
        redisTemplate.opsForValue().set("email", "vishal.adhikari36@gmail.com");
        String email = redisTemplate.opsForValue().get("email");
        assertEquals("vishal.adhikari36@gmail.com", email);

        String weather = redisTemplate.opsForValue().get("weather_of_Mumbai");
        System.out.println(weather);
    }

}
