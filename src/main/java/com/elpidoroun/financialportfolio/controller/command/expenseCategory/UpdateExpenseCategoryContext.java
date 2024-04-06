package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;

import static java.util.Objects.requireNonNull;

public class UpdateExpenseCategoryContext {

    private final ExpenseCategory original;
    private final ExpenseCategory entity;

    public UpdateExpenseCategoryContext(ExpenseCategory original, ExpenseCategory entity){
        this.original = requireNonNull(original, "Original ExpenseCategory is missing");
        this.entity = requireNonNull(entity, "new ExpenseCategory is missing");
    }

    public ExpenseCategory getOriginal(){ return original; }
    public ExpenseCategory getEntity(){ return entity; }
}