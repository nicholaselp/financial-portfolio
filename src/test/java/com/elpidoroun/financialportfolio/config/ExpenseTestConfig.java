package com.elpidoroun.financialportfolio.config;

import com.elpidoroun.financialportfolio.controller.command.expense.CreateExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.DeleteExpenseCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.GetAllExpensesCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.GetExpenseByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.ImportExpensesCommand;
import com.elpidoroun.financialportfolio.controller.command.expense.UpdateExpenseCommand;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.mappers.ImportRequestLineMapper;
import com.elpidoroun.financialportfolio.mappers.ImportRequestMapper;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepositoryStub;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseRepositoryStub;
import com.elpidoroun.financialportfolio.repository.ImportRequestDao;
import com.elpidoroun.financialportfolio.repository.ImportRequestDaoStub;
import com.elpidoroun.financialportfolio.repository.ImportRequestLineRepository;
import com.elpidoroun.financialportfolio.repository.ImportRequestLineRepositoryStub;
import com.elpidoroun.financialportfolio.repository.ImportRequestRepository;
import com.elpidoroun.financialportfolio.repository.ImportRequestRepositoryStub;
import com.elpidoroun.financialportfolio.service.ValidationService;
import com.elpidoroun.financialportfolio.service.cache.ExpenseCategoryCacheService;
import com.elpidoroun.financialportfolio.service.expense.CreateExpenseService;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expense.GetExpenseService;
import com.elpidoroun.financialportfolio.service.expense.ImportExpenseService;
import com.elpidoroun.financialportfolio.service.expense.UpdateExpenseService;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.service.kafka.ImpReqLineEventProducer;
import com.elpidoroun.financialportfolio.service.normalize.ExpenseCategoryNormalizer;
import com.elpidoroun.financialportfolio.service.validation.expense.ExpenseExistsValidation;
import com.elpidoroun.financialportfolio.service.validation.expense.ExpenseStatusValidation;
import com.elpidoroun.financialportfolio.service.validation.expense.ExpenseUniquenessValidator;
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
        importExpenseService = new ImportExpenseService(createExpenseService, importRequestRepository,
                importRequestLineRepository, impReqLineEventProducer, importRequestLineMapper);
        importExpenseCommand = new ImportExpensesCommand(importExpenseService);
    }


}