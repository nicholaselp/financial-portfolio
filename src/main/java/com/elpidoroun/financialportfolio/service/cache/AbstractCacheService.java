package com.elpidoroun.financialportfolio.service.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

public abstract class AbstractCacheService<T> {

    protected final RedisTemplate<String, T> redisTemplate;

    protected AbstractCacheService(RedisTemplate<String, T> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public Optional<T> get(String key){
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }
}