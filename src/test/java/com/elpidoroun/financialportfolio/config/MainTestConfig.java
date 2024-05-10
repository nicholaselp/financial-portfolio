package com.elpidoroun.financialportfolio.config;

public class MainTestConfig {

    public ExpenseCategoryTestConfig expenseCategoryTestConfig = new ExpenseCategoryTestConfig();
    public ExpenseTestConfig expenseTestConfig = new ExpenseTestConfig();

    public MainTestConfig(){ }

    public ExpenseCategoryTestConfig getExpenseCategoryTestConfig() { return expenseCategoryTestConfig; }
    public ExpenseTestConfig getExpenseTestConfig(){ return expenseTestConfig; }
}
