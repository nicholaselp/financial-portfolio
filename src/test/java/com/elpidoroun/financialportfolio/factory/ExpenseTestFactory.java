package com.elpidoroun.financialportfolio.factory;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.StatusDto;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.Status;

import java.math.BigDecimal;

import static com.elpidoroun.financialportfolio.factory.ExpenseCategoryTestFactory.createExpenseCategory;
import static com.elpidoroun.financialportfolio.factory.ExpenseCategoryTestFactory.createExpenseCategoryDto;
import static com.elpidoroun.financialportfolio.repository.ExpenseRepositoryStub.generateUniqueId;

public class ExpenseTestFactory {

    public static Expense createExpense(){
        return createExpense("name", createExpenseCategory(), Status.ACTIVE);
    }

    public static Expense createExpense(String name){
        return createExpense(name, createExpenseCategory(), Status.ACTIVE);
    }

    public static Expense createExpense(Status status){
        return createExpense("name", createExpenseCategory(), status);
    }

    public static Expense createExpense(String name, ExpenseCategory expenseCategory){
        return createExpense(name, expenseCategory, Status.ACTIVE);
    }

    public static Expense createExpense(ExpenseCategory expenseCategory){
        return createExpense("expenseName", expenseCategory, Status.ACTIVE);
    }

    public static Expense createExpense(String expenseName, ExpenseCategory expenseCategory,
                                        Status status){
        return Expense.builder()
                .withId(generateUniqueId())
                .withExpenseName(expenseName)
                .withExpenseCategory(expenseCategory)
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(status)
                .build();
    }

    public static ExpenseDto createExpenseDto(){
        return createExpenseDto("name", createExpenseCategoryDto());
    }

    public static ExpenseDto createExpenseDto(String expenseName){
        return createExpenseDto(expenseName, createExpenseCategoryDto());
    }

    public static ExpenseDto createExpenseDto(String name, ExpenseCategoryDto expenseCategoryDto){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseCategory(expenseCategoryDto);
        expenseDto.setExpenseName(name);
        expenseDto.setNote("expense note");
        expenseDto.setMonthlyAllocatedAmount(new BigDecimal("100.00"));
        expenseDto.setYearlyAllocatedAmount(new BigDecimal("1200.00"));
        expenseDto.setStatus(StatusDto.ACTIVE);
        return expenseDto;
    }

    public static ExpenseDto createExpenseDto(Long expenseCategoryId){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategoryDto());
        expenseDto.setExpenseName("rent");
        expenseDto.setNote("expense note");
        expenseDto.setMonthlyAllocatedAmount(new BigDecimal("100.00"));
        expenseDto.setYearlyAllocatedAmount(new BigDecimal("1200.00"));
        expenseDto.setStatus(StatusDto.ACTIVE);
        expenseDto.setExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategoryDto(expenseCategoryId));
        return expenseDto;
    }

}
