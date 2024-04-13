package com.elpidoroun.financialportfolio.controller.command.expenseCategory;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class UpdateExpenseCategoryContext {

    @NonNull private final ExpenseCategory original;
    @NonNull private final ExpenseCategory entity;
}