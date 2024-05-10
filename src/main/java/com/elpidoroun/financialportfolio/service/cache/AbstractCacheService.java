package com.elpidoroun.financialportfolio.service.cache;

import lombok.NonNull;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

public abstract class AbstractCacheService<T> {

    @NonNull protected final RedisTemplate<String, T> redisTemplate;
    @NonNull private final String key;

    protected AbstractCacheService(@NonNull RedisTemplate<String, T> redisTemplate, @NonNull String key){
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    public Optional<T> getById(String id){
        return  Optional.ofNullable((T)redisTemplate.opsForHash().get(key, id));
    }

    public void addToCache(String id, T entity){
        redisTemplate.opsForHash().put(key, id, entity);
    }

    public void deleteFromCache(String id){
        redisTemplate.opsForHash().delete(key, id);
    }
}