package com.elpidoroun.factory;

import com.elpidoroun.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.generated.dto.ExpenseDto;
import com.elpidoroun.generated.dto.StatusDto;
import com.elpidoroun.model.Expense;
import com.elpidoroun.model.ExpenseCategory;
import com.elpidoroun.model.Status;
import com.elpidoroun.repository.ExpenseRepositoryStub;

import java.math.BigDecimal;

public class ExpenseTestFactory {

    public static Expense createExpense(){
        return createExpense("name", ExpenseCategoryTestFactory.createExpenseCategory(), Status.ACTIVE);
    }

    public static Expense createExpense(String name){
        return createExpense(name, ExpenseCategoryTestFactory.createExpenseCategory(), Status.ACTIVE);
    }

    public static Expense createExpense(Status status){
        return createExpense("name", ExpenseCategoryTestFactory.createExpenseCategory(), status);
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
                .withId(ExpenseRepositoryStub.generateUniqueId())
                .withExpenseName(expenseName)
                .withExpenseCategory(expenseCategory)
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(status)
                .build();
    }

    public static ExpenseDto createExpenseDto(){
        return createExpenseDto("name", ExpenseCategoryTestFactory.createExpenseCategoryDto());
    }

    public static ExpenseDto createExpenseDto(String expenseName){
        return createExpenseDto(expenseName, ExpenseCategoryTestFactory.createExpenseCategoryDto());
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
