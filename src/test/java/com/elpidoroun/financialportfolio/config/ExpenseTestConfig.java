package com.elpidoroun.financialportfolio.config;

import com.elpidoroun.financialportfolio.controller.command.expense.CreateExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.DeleteExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.GetAllExpensesCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.GetExpenseByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseCommand;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepositoryStub;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseRepositoryStub;
import com.elpidoroun.financialportfolio.service.ValidationService;
import com.elpidoroun.financialportfolio.service.cache.ExpenseCategoryCacheService;
import com.elpidoroun.financialportfolio.service.expense.CreateExpenseService;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import com.elpidoroun.financialportfolio.service.expense.UpdateExpenseService;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.service.normalize.ExpenseCategoryNormalizer;
import com.elpidoroun.financialportfolio.service.validation.expense.ExpenseExistsValidation;
import com.elpidoroun.financialportfolio.service.validation.expense.ExpenseUniquenessValidator;
import lombok.Getter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Getter
public class ExpenseTestConfig {

    ExpenseRepository expenseRepository = new ExpenseRepositoryStub();
    ExpenseCategoryRepository expenseCategoryRepository = new ExpenseCategoryRepositoryStub();
    ExpenseRepositoryOperations expenseRepositoryOperations;
    ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;
    CreateExpenseService createExpenseService;
    UpdateExpenseService updateExpenseService;
    GetExpenseService getExpenseService;
    CreateExpenseCommand createExpenseCommand;
    UpdateExpenseCommand updateExpenseCommand;
    GetExpenseByIdCommand getExpenseByIdCommand;
    GetAllExpensesCommand getAllExpensesCommand;
    DeleteExpenseCommand deleteExpenseCommand;
    ExpenseMapper expenseMapper;
    ExpenseCategoryMapper expenseCategoryMapper;
    RedisTemplate<String, ExpenseCategory> expenseCategoryRedisTemplate;
    ExpenseCategoryCacheService expenseCategoryCacheService;


    public ExpenseTestConfig(){
        expenseCategoryRedisTemplate = initializeRedisTemplate();

        expenseCategoryCacheService = new ExpenseCategoryCacheService(expenseCategoryRedisTemplate);

        expenseRepositoryOperations = new ExpenseRepositoryOperations(expenseRepository);
        expenseCategoryRepositoryOperations = new ExpenseCategoryRepositoryOperations(expenseCategoryRepository, expenseCategoryCacheService);
        var validations = new ValidationService<>(List.of(new ExpenseUniquenessValidator(expenseRepositoryOperations), new ExpenseExistsValidation(expenseRepositoryOperations)));
        var normalizer = new ExpenseCategoryNormalizer(expenseCategoryCacheService);
        createExpenseService = new CreateExpenseService(expenseRepositoryOperations, validations, normalizer);
        updateExpenseService = new UpdateExpenseService(expenseRepositoryOperations, validations, normalizer);
        getExpenseService = new GetExpenseService(expenseRepositoryOperations);
        expenseCategoryMapper = new ExpenseCategoryMapper();
        expenseMapper = new ExpenseMapper(expenseCategoryMapper);
        createExpenseCommand = new CreateExpenseCommand(createExpenseService, expenseMapper);
        updateExpenseCommand = new UpdateExpenseCommand(updateExpenseService, expenseMapper, expenseRepositoryOperations);
        getExpenseByIdCommand = new GetExpenseByIdCommand(getExpenseService, expenseMapper);
        getAllExpensesCommand = new GetAllExpensesCommand(getExpenseService, expenseMapper);
        deleteExpenseCommand = new DeleteExpenseCommand(expenseRepositoryOperations);
    }

    private RedisTemplate<String, ExpenseCategory> initializeRedisTemplate() {
        RedisTemplate<String, ExpenseCategory> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(createConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet(); // Ensures template is fully initialized
        return redisTemplate;
    }

    private RedisConnectionFactory createConnectionFactory() {
        String host = "localhost";
        int port = 6379;

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(host, port);
        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }
}
