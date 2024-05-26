package com.elpidoroun.financialportfolio.config;

import com.elpidoroun.financialportfolio.controller.command.expenseCategory.CreateExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.DeleteExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.GetAllExpenseCategoriesCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.GetExpenseCategoryByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.UpdateExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepositoryStub;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseRepositoryStub;
import com.elpidoroun.financialportfolio.service.ValidationService;
import com.elpidoroun.financialportfolio.service.cache.ExpenseCategoryCacheService;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expenseCategory.CreateExpenseCategoryService;
import com.elpidoroun.financialportfolio.service.expenseCategory.DeleteExpenseCategoryService;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expenseCategory.GetExpenseCategoryService;
import com.elpidoroun.financialportfolio.service.expenseCategory.UpdateExpenseCategoryService;
import com.elpidoroun.financialportfolio.service.normalize.ExpenseCategoryNormalizer;
import com.elpidoroun.financialportfolio.service.validation.expenseCategory.ExpenseCategoryExistsValidation;
import com.elpidoroun.financialportfolio.service.validation.expenseCategory.ExpenseCategoryMandatoryFieldsValidator;
import com.elpidoroun.financialportfolio.service.validation.expenseCategory.ExpenseCategoryNameValidator;
import com.elpidoroun.financialportfolio.service.validation.expenseCategory.ExpenseCategoryUniquenessValidator;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Getter
public class ExpenseCategoryTestConfig extends CommonTestConfig{
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