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
import com.elpidoroun.financialportfolio.service.expense.CreateExpenseService;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import com.elpidoroun.financialportfolio.service.expense.UpdateExpenseService;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.service.normalize.ExpenseCategoryNormalizer;
import com.elpidoroun.financialportfolio.service.validation.expense.ExpenseUniquenessValidator;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    public ExpenseTestConfig(){
        expenseRepositoryOperations = new ExpenseRepositoryOperations(expenseRepository);
        expenseCategoryRepositoryOperations = new ExpenseCategoryRepositoryOperations(expenseCategoryRepository);
        var validations = new ValidationService<>(List.of(new ExpenseUniquenessValidator(expenseRepositoryOperations)));
        expenseCategoryRedisTemplate = mock(RedisTemplate.class);
        var normalizer = new ExpenseCategoryNormalizer(expenseCategoryRedisTemplate);
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

    public void mockNormalizerResponse(ExpenseCategory expenseCategory){
        ValueOperations<String, ExpenseCategory> valueOperationsMock = mock(ValueOperations.class);
        when(expenseCategoryRedisTemplate.opsForValue()).thenReturn(valueOperationsMock);

        when(expenseCategoryRedisTemplate.opsForValue().get(any())).thenReturn(expenseCategory);
    }

    public void mockNormalizerReturnNull(){
        ValueOperations<String, ExpenseCategory> valueOperationsMock = mock(ValueOperations.class);
        when(expenseCategoryRedisTemplate.opsForValue()).thenReturn(valueOperationsMock);

        when(expenseCategoryRedisTemplate.opsForValue().get(any())).thenReturn(null);
    }

}
