package com.elpidoroun.financialportfolio.config;

public class MainTestConfig {

    ExpenseCategoryTestConfig expenseCategoryTestConfig = new ExpenseCategoryTestConfig();
    ExpenseTestConfig expenseTestConfig = new ExpenseTestConfig();

    public MainTestConfig(){ }

    public ExpenseCategoryTestConfig getExpenseCategoryTestConfig() { return expenseCategoryTestConfig; }
    public ExpenseTestConfig getExpenseTestConfig(){ return expenseTestConfig; }
}
