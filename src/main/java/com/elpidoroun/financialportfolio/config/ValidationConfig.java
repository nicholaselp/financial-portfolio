package com.elpidoroun.financialportfolio.config;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.ValidationService;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.service.validation.EntityValidator;
import com.elpidoroun.financialportfolio.service.validation.expense.ExpenseUniquenessValidator;
import com.elpidoroun.financialportfolio.service.validation.expenseCategory.ExpenseCategoryNameValidator;
import com.elpidoroun.financialportfolio.service.validation.expenseCategory.ExpenseCategoryUniquenessValidator;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Configuration
public class ValidationConfig {

    @NonNull ExpenseRepositoryOperations expenseRepositoryOperations;
    @NonNull ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;

    @Bean
    public ValidationService<Expense> expenseValidationService(){
        List<EntityValidator<Expense>> expenseValidators = new ArrayList<>();

        expenseValidators.add(new ExpenseUniquenessValidator(expenseRepositoryOperations));

        return new ValidationService<>(expenseValidators);
    }

    @Bean
    public ValidationService<ExpenseCategory> expenseCategoryValidationService(){
        List<EntityValidator<ExpenseCategory>> expenseCategoryValidators = new ArrayList<>();

        expenseCategoryValidators.add(new ExpenseCategoryNameValidator());
        expenseCategoryValidators.add(new ExpenseCategoryUniquenessValidator(expenseCategoryRepositoryOperations));

        return new ValidationService<>(expenseCategoryValidators);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}
