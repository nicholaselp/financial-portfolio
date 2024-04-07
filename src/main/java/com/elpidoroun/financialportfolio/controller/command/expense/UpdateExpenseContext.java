package com.elpidoroun.financialportfolio.controller.command.expense;

import com.elpidoroun.financialportfolio.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class UpdateExpenseContext {
    @NonNull private final Expense original;
    @NonNull private final Expense entity;
}