package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.StatusDto;

import java.math.BigDecimal;

public class ExpenseTestFactory {

    public static Expense createExpense(){
        return Expense.builder()
                .withExpenseName("expenseName")
                .withExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategory())
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.ACTIVE)
                .build();
    }

    public static Expense createExpense(String expenseName){
        return Expense.builder()
                .withExpenseName(expenseName)
                .withExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategory())
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.ACTIVE)
                .build();
    }

    public static Expense createExpense(String expenseName, ExpenseCategory expenseCategory){
        return Expense.builder()
                .withExpenseName(expenseName)
                .withExpenseCategory(expenseCategory)
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.ACTIVE)
                .build();
    }

    public static Expense createExpenseWithId(){
        return Expense.builder(1L)
                .withExpenseName("expenseName")
                .withExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategory())
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.ACTIVE)
                .build();
    }

    public static Expense createDeletedExpense(){
        return Expense.builder()
                .withExpenseName("expenseName")
                .withExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategory())
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.DELETED)
                .withExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategory())
                .build();
    }

    public static Expense createDeletedExpense(ExpenseCategory expenseCategory){
        return Expense.builder()
                .withExpenseName("expenseName")
                .withExpenseCategory(expenseCategory)
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.DELETED)
                .build();
    }

    public static Expense createExpense(ExpenseCategory category){
        return Expense.builder()
                .withExpenseName("expenseName")
                .withExpenseCategory(category)
                .withYearlyAllocatedAmount(new BigDecimal("120.00"))
                .withMonthlyAllocatedAmount(new BigDecimal("10.00"))
                .withStatus(Status.ACTIVE)
                .build();
    }

    public static ExpenseDto createExpenseDto(){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategoryDto());
        expenseDto.setExpenseName("rent");
        expenseDto.setNote("expense note");
        expenseDto.setMonthlyAllocatedAmount(new BigDecimal("100.00"));
        expenseDto.setYearlyAllocatedAmount(new BigDecimal("1200.00"));
        expenseDto.setStatus(StatusDto.ACTIVE);
        return expenseDto;
    }

    public static ExpenseDto createExpenseDto(String expenseName){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseCategory(ExpenseCategoryTestFactory.createExpenseCategoryDto());
        expenseDto.setExpenseName(expenseName);
        expenseDto.setNote("expense note");
        expenseDto.setMonthlyAllocatedAmount(new BigDecimal("100.00"));
        expenseDto.setYearlyAllocatedAmount(new BigDecimal("1200.00"));
        expenseDto.setStatus(StatusDto.ACTIVE);
        return expenseDto;
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
