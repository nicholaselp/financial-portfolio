package com.elpidoroun.financialportfolio.service.normalize;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.service.cache.AbstractCacheService;
import com.elpidoroun.financialportfolio.utilities.Result;
import lombok.NonNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;
import static java.util.Objects.isNull;

@Component
public class ExpenseCategoryNormalizer extends AbstractCacheService<ExpenseCategory> {

    public ExpenseCategoryNormalizer(@NonNull RedisTemplate<String, ExpenseCategory> expenseCategoryRedisTemplate){
        super(expenseCategoryRedisTemplate);
    }

    public Result<Expense, String> normalize(Expense expense){
        var expenseCategoryId = expense.getExpenseCategory().getId();
        if(isNull(expenseCategoryId)){
            return Result.fail("Failed during normalization. ExpenseCategory ID is missing");
        }

        return get(EXPENSE_CATEGORY_CACHE + "::" +expenseCategoryId)
                .<Result<Expense, String>>map(expenseCategory ->
                        Result.success(expense.clone().withExpenseCategory(expenseCategory).build()))
                .orElseGet(() -> Result.fail("ExpenseCategory with ID: " + expenseCategoryId + " not found during normalization"));
    }
}