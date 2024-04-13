package com.elpidoroun.financialportfolio.config;

import com.elpidoroun.financialportfolio.controller.command.expenseCategory.CreateExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.DeleteExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.GetAllExpenseCategoriesCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.GetExpenseCategoryByIdCommand;
import com.elpidoroun.financialportfolio.controller.command.expenseCategory.UpdateExpenseCategoryCommand;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepositoryStub;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseRepositoryStub;
import com.elpidoroun.financialportfolio.service.ValidationService;
import com.elpidoroun.financialportfolio.service.expense.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expenseCategory.CreateExpenseCategoryService;
import com.elpidoroun.financialportfolio.service.expenseCategory.ExpenseCategoryRepositoryOperations;
import com.elpidoroun.financialportfolio.service.expenseCategory.GetExpenseCategoryService;
import com.elpidoroun.financialportfolio.service.expenseCategory.UpdateExpenseCategoryService;
import com.elpidoroun.financialportfolio.service.validation.expenseCategory.ExpenseCategoryNameValidator;
import com.elpidoroun.financialportfolio.service.validation.expenseCategory.ExpenseCategoryUniquenessValidator;
import lombok.Getter;

import java.util.List;

@Getter
public class ExpenseCategoryTestConfig {
    private final ExpenseCategoryRepository expenseCategoryRepository = new ExpenseCategoryRepositoryStub();
    private final ExpenseRepository expenseRepository = new ExpenseRepositoryStub();
    private final ExpenseCategoryRepositoryOperations expenseCategoryRepositoryOperations;
    private final ExpenseRepositoryOperations expenseRepositoryOperations;
    private final CreateExpenseCategoryService createExpenseCategoryService;
    private final UpdateExpenseCategoryService updateExpenseCategoryService;
    private final GetExpenseCategoryService getExpenseCategoryService;
    private final CreateExpenseCategoryCommand createExpenseCategoryCommand;
    private final UpdateExpenseCategoryCommand updateExpenseCategoryCommand;
    private final GetAllExpenseCategoriesCommand getAllExpenseCategoriesCommand;
    private final GetExpenseCategoryByIdCommand getExpenseCategoryByIdCommand;
    private final DeleteExpenseCategoryCommand deleteExpenseCategoryCommand;

    protected ExpenseCategoryTestConfig(){
        expenseCategoryRepositoryOperations = new ExpenseCategoryRepositoryOperations(expenseCategoryRepository);
        expenseRepositoryOperations = new ExpenseRepositoryOperations(expenseRepository);
        var validationService = new ValidationService<>(List.of(
                new ExpenseCategoryNameValidator(), new ExpenseCategoryUniquenessValidator(expenseCategoryRepositoryOperations)));

        createExpenseCategoryService = new CreateExpenseCategoryService(expenseCategoryRepositoryOperations, validationService);
        updateExpenseCategoryService = new UpdateExpenseCategoryService(expenseCategoryRepositoryOperations, validationService);
        getExpenseCategoryService = new GetExpenseCategoryService(expenseCategoryRepositoryOperations);
        createExpenseCategoryCommand = new CreateExpenseCategoryCommand(createExpenseCategoryService, new ExpenseCategoryMapper());
        updateExpenseCategoryCommand = new UpdateExpenseCategoryCommand(updateExpenseCategoryService, new ExpenseCategoryMapper(), expenseCategoryRepositoryOperations);
        getAllExpenseCategoriesCommand = new GetAllExpenseCategoriesCommand(getExpenseCategoryService, new ExpenseCategoryMapper());
        getExpenseCategoryByIdCommand = new GetExpenseCategoryByIdCommand(getExpenseCategoryService, new ExpenseCategoryMapper());
        deleteExpenseCategoryCommand = new DeleteExpenseCategoryCommand(expenseCategoryRepositoryOperations, expenseRepositoryOperations);
    }
}