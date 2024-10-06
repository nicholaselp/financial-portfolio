package com.elpidoroun.service.cache;

import com.elpidoroun.model.ExpenseCategory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import static com.elpidoroun.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;

@Component
public class ExpenseCategoryCacheService extends AbstractCacheService<ExpenseCategory> {

    public ExpenseCategoryCacheService(RedisTemplate<String, ExpenseCategory> redisTemplate) {
        super(redisTemplate, EXPENSE_CATEGORY_CACHE);
    }
}