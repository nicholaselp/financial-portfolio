package com.elpidoroun.config;

import com.elpidoroun.controller.command.expense.CreateExpenseCommand;
import com.elpidoroun.controller.command.expense.DeleteExpenseCommand;
import com.elpidoroun.controller.command.expense.GetAllExpensesCommand;
import com.elpidoroun.controller.command.expense.GetExpenseByIdCommand;
import com.elpidoroun.controller.command.expense.ImportExpensesCommand;
import com.elpidoroun.controller.command.expense.UpdateExpenseCommand;
import com.elpidoroun.mappers.ExpenseCategoryMapper;
import com.elpidoroun.mappers.ExpenseMapper;
import com.elpidoroun.mappers.ImportRequestLineMapper;
import com.elpidoroun.mappers.ImportRequestMapper;
import com.elpidoroun.model.ExpenseCategory;
import com.elpidoroun.repository.ExpenseCategoryRepository;
import com.elpidoroun.repository.ExpenseCategoryRepositoryStub;
import com.elpidoroun.repository.ExpenseRepository;
import com.elpidoroun.repository.ExpenseRepositoryStub;
import com.elpidoroun.repository.ImportRequestDao;
import com.elpidoroun.repository.ImportRequestDaoStub;
import com.elpidoroun.repository.ImportRequestLineRepository;
import com.elpidoroun.repository.ImportRequestLineRepositoryStub;
import com.elpidoroun.repository.ImportRequestRepository;
import com.elpidoroun.repository.ImportRequestRepositoryStub;
import com.elpidoroun.service.cache.ExpenseCategoryCacheService;
import com.elpidoroun.service.expense.CreateExpenseService;
import com.elpidoroun.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.service.expense.GetExpenseService;
import com.elpidoroun.service.expense.ImportExpenseService;
import com.elpidoroun.service.expense.UpdateExpenseService;
import com.elpidoroun.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.service.kafka.ImpReqLineEventProducer;
import com.elpidoroun.service.normalize.ExpenseCategoryNormalizer;
import com.elpidoroun.service.validation.ValidationService;
import com.elpidoroun.service.validation.expense.ExpenseExistsValidation;
import com.elpidoroun.service.validation.expense.ExpenseStatusValidation;
import com.elpidoroun.service.validation.expense.ExpenseUniquenessValidator;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.mockito.Mockito.mock;

@Getter
public class ExpenseTestConfig extends CommonTestConfig{

    ExpenseRepository expenseRepository = new ExpenseRepositoryStub();
    ExpenseCategoryRepository expenseCategoryRepository = new ExpenseCategoryRepositoryStub();
    ImportRequestRepository importRequestRepository = new ImportRequestRepositoryStub();
    ImportRequestLineRepository importRequestLineRepository = new ImportRequestLineRepositoryStub();
    ImportRequestDao importRequestDao = new ImportRequestDaoStub(importRequestRepository);

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
    RedisTemplate<String, ExpenseCategory> expenseCategoryRedisTemplate = createMockRedisTemplate();
    ExpenseCategoryCacheService expenseCategoryCacheService;
    ImportExpenseService importExpenseService;
    ImpReqLineEventProducer impReqLineEventProducer = mock(ImpReqLineEventProducer.class);
    ImportRequestLineMapper importRequestLineMapper;
    ImportRequestMapper importRequestMapper;
    ImportExpensesCommand importExpenseCommand;


    public ExpenseTestConfig(){
        expenseCategoryCacheService = new ExpenseCategoryCacheService(expenseCategoryRedisTemplate);

        expenseRepositoryOperations = new ExpenseRepositoryOperations(expenseRepository);
        expenseCategoryRepositoryOperations = new ExpenseCategoryRepositoryOperations(expenseCategoryRepository, expenseCategoryCacheService);
        var validations = new ValidationService<>(List.of(new ExpenseUniquenessValidator(expenseRepositoryOperations),
                new ExpenseExistsValidation(expenseRepositoryOperations), new ExpenseStatusValidation()));
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

        importRequestMapper = new ImportRequestMapper();
        importRequestLineMapper = new ImportRequestLineMapper(importRequestMapper);
        importExpenseService = new ImportExpenseService(importRequestLineRepository, impReqLineEventProducer, importRequestLineMapper);
        importExpenseCommand = new ImportExpensesCommand(importExpenseService, importRequestRepository);
    }


}