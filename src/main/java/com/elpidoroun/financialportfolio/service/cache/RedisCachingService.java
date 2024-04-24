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
        var expenseCategoryList = expenseCategoryRepository.findAll();

        expenseCategoryList.forEach(expenseCategory -> {
            String key = EXPENSE_CATEGORY_CACHE+"::" + expenseCategory.getId();
            expenseCategoryRedis.opsForValue().set(key, expenseCategory);
                });
        logger.info("==========================================================");
        logger.info("Cached ExpenseCategory items: " + expenseCategoryList.size());
        logger.info("==========================================================");
    }
}