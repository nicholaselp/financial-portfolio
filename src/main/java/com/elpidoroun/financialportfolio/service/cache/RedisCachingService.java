package com.elpidoroun.financialportfolio.service.cache;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;

@Service
@AllArgsConstructor
public class RedisCachingService {

    private static final Logger logger = LoggerFactory.getLogger(RedisCachingService.class);

    @NonNull private final ExpenseCategoryRepository expenseCategoryRepository;
    @NonNull private RedisTemplate<String, ExpenseCategory> expenseCategoryRedis;

    @PostConstruct
    public void cacheExpenseCategoriesFromDatabase(){
        flushCache();
        var expenseCategoryList = expenseCategoryRepository.findAll();

        logger.info("==========================================================");
        expenseCategoryList.forEach(expenseCategory -> {
            logger.info("ExpenseCategory with ID: " + expenseCategory.getId() + " name: " + expenseCategory.getCategoryName());
            expenseCategoryRedis.opsForHash().put(EXPENSE_CATEGORY_CACHE, expenseCategory.getId().toString(), expenseCategory);
        });
        logger.info("==========================================================");
    }

    private void flushCache(){
        expenseCategoryRedis.getConnectionFactory().getConnection().flushAll();
    }
}