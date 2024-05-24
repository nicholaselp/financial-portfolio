package com.elpidoroun.financialportfolio.service.cache;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;

@Component
public class ExpenseCategoryRedisCacheService extends AbstractCacheService<ExpenseCategory>{

    public ExpenseCategoryRedisCacheService(RedisTemplate<String, ExpenseCategory> redisTemplate) {
        super(redisTemplate, EXPENSE_CATEGORY_CACHE);
    }
}
