package com.elpidoroun.financialportfolio.config;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import lombok.Getter;
import org.mockito.Mockito;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Getter
public class CommonTestConfig {

    protected RedisTemplate<String, ExpenseCategory> expenseCategoryRedisTemplate;

    protected RedisTemplate<String, ExpenseCategory> createMockRedisTemplate() {
        RedisTemplate<String, ExpenseCategory> redisTemplate = Mockito.mock(RedisTemplate.class);
        HashOperations<String, String, ExpenseCategory> hashOperations = Mockito.mock(HashOperations.class);

        Mockito.<HashOperations<String, String, ExpenseCategory>>when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        Mockito.doNothing().when(redisTemplate).setKeySerializer(Mockito.any(StringRedisSerializer.class));
        Mockito.doNothing().when(redisTemplate).setHashKeySerializer(Mockito.any(StringRedisSerializer.class));
        Mockito.doNothing().when(redisTemplate).setValueSerializer(Mockito.any(GenericJackson2JsonRedisSerializer.class));

        return redisTemplate;
    }
}