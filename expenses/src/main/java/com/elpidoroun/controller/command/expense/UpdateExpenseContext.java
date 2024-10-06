package com.elpidoroun.controller.command.expense;

import com.elpidoroun.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class UpdateExpenseContext {
    @NonNull private final Expense original;
    @NonNull private final Expense entity;
}