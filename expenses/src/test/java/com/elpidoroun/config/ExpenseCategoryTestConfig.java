package com.elpidoroun.config;

import com.elpidoroun.controller.command.expenseCategory.CreateExpenseCategoryCommand;
import com.elpidoroun.controller.command.expenseCategory.DeleteExpenseCategoryCommand;
import com.elpidoroun.controller.command.expenseCategory.GetAllExpenseCategoriesCommand;
import com.elpidoroun.controller.command.expenseCategory.GetExpenseCategoryByIdCommand;
import com.elpidoroun.controller.command.expenseCategory.UpdateExpenseCategoryCommand;
import com.elpidoroun.repository.ExpenseCategoryRepositoryStub;
import com.elpidoroun.repository.ExpenseRepositoryStub;
import com.elpidoroun.mappers.ExpenseCategoryMapper;
import com.elpidoroun.model.ExpenseCategory;
import com.elpidoroun.repository.ExpenseCategoryRepository;
import com.elpidoroun.repository.ExpenseRepository;
import com.elpidoroun.service.cache.ExpenseCategoryCacheService;
import com.elpidoroun.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.service.expenseCategory.CreateExpenseCategoryService;
import com.elpidoroun.service.expenseCategory.DeleteExpenseCategoryService;
import com.elpidoroun.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.service.expenseCategory.GetExpenseCategoryService;
import com.elpidoroun.service.expenseCategory.UpdateExpenseCategoryService;
import com.elpidoroun.service.normalize.ExpenseCategoryNormalizer;
import com.elpidoroun.service.validation.ValidationService;
import com.elpidoroun.service.validation.expenseCategory.ExpenseCategoryExistsValidation;
import com.elpidoroun.service.validation.expenseCategory.ExpenseCategoryMandatoryFieldsValidator;
import com.elpidoroun.service.validation.expenseCategory.ExpenseCategoryNameValidator;
import com.elpidoroun.service.validation.expenseCategory.ExpenseCategoryUniquenessValidator;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Getter
public class ExpenseCategoryTestConfig extends CommonTestConfig {
    private final ExpenseCategoryRepository expenseCategoryRepository = new ExpenseCategoryRepositoryStub();
    private final ExpenseRepository expenseRepository = new ExpenseRepositoryStub();
    private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;
    private final ExpenseRepositoryOperations expenseRepositoryOperations;
    private final CreateExpenseCategoryService createExpenseCategoryService;
    private final UpdateExpenseCategoryService updateExpenseCategoryService;
    private final GetExpenseCategoryService getExpenseCategoryService;
    private final DeleteExpenseCategoryService deleteExpenseCategoryService;

    private final CreateExpenseCategoryCommand createExpenseCategoryCommand;
    private final UpdateExpenseCategoryCommand updateExpenseCategoryCommand;
    private final GetAllExpenseCategoriesCommand getAllExpenseCategoriesCommand;
    private final GetExpenseCategoryByIdCommand getExpenseCategoryByIdCommand;
    private final DeleteExpenseCategoryCommand deleteExpenseCategoryCommand;

    private final RedisTemplate<String, ExpenseCategory> expenseCategoryRedisTemplate = createMockRedisTemplate();

    private final ExpenseCategoryCacheService expenseCategoryCacheService;
    private final ExpenseCategoryNormalizer expenseCategoryNormalizer;

    protected ExpenseCategoryTestConfig(){
        expenseCategoryCacheService = new ExpenseCategoryCacheService(expenseCategoryRedisTemplate);

        expenseCategoryRepositoryOperations = new ExpenseCategoryRepositoryOperations(expenseCategoryRepository, expenseCategoryCacheService);
        expenseRepositoryOperations = new ExpenseRepositoryOperations(expenseRepository);
        var validationService = new ValidationService<>(List.of(new ExpenseCategoryMandatoryFieldsValidator(),
                new ExpenseCategoryNameValidator(), new ExpenseCategoryUniquenessValidator(expenseCategoryRepositoryOperations),
                new ExpenseCategoryExistsValidation(expenseCategoryRepositoryOperations)));

        createExpenseCategoryService = new CreateExpenseCategoryService(expenseCategoryRepositoryOperations, validationService);
        updateExpenseCategoryService = new UpdateExpenseCategoryService(expenseCategoryRepositoryOperations, validationService);
        getExpenseCategoryService = new GetExpenseCategoryService(expenseCategoryRepositoryOperations);
        deleteExpenseCategoryService = new DeleteExpenseCategoryService(expenseCategoryRepositoryOperations, expenseRepositoryOperations);

        createExpenseCategoryCommand = new CreateExpenseCategoryCommand(createExpenseCategoryService, new ExpenseCategoryMapper());
        updateExpenseCategoryCommand = new UpdateExpenseCategoryCommand(updateExpenseCategoryService, new ExpenseCategoryMapper(), expenseCategoryRepositoryOperations);
        getAllExpenseCategoriesCommand = new GetAllExpenseCategoriesCommand(getExpenseCategoryService, new ExpenseCategoryMapper());
        getExpenseCategoryByIdCommand = new GetExpenseCategoryByIdCommand(getExpenseCategoryService, new ExpenseCategoryMapper());
        deleteExpenseCategoryCommand = new DeleteExpenseCategoryCommand(deleteExpenseCategoryService);
        expenseCategoryNormalizer = new ExpenseCategoryNormalizer(expenseCategoryCacheService);
    }
}