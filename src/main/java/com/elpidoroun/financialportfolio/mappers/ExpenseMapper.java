package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.generated.dto.MetadataDto;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.model.Status;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class ExpenseMapper {

    @NonNull ExpenseCategoryMapper expenseCategoryMapper;

    public ExpenseResponseDto convertToResponseDto(Expense expense){
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setExpense(convertToDto(expense));
        MetadataDto meta = new MetadataDto();
        meta.setId(expense.getId());
        meta.setCreatedAt(expense.getCreatedAt());
        expenseResponseDto.setMeta(meta);

        return expenseResponseDto;
    }

    public ExpenseDto convertToDto(Expense expense){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseName(expense.getExpenseName());
        expenseDto.setMonthlyAllocatedAmount(expense.getMonthlyAllocatedAmount());
        expenseDto.setYearlyAllocatedAmount(expense.getYearlyAllocatedAmount());
        expenseDto.setStatus(StatusMapper.toDto(expense.getStatus()));
        expenseDto.setExpenseCategory(expenseCategoryMapper.convertToDto(expense.getExpenseCategory()));
        expense.getNote().ifPresent(expenseDto::setNote);
        return expenseDto;
    }

    public Expense convertToDomain(ExpenseResponseDto expenseResponseDto){
        return Expense.builder()
                .withId(expenseResponseDto.getMeta().getId())
                .withCreatedAt(expenseResponseDto.getMeta().getCreatedAt())
                .withExpenseName(expenseResponseDto.getExpense().getExpenseName())
                .withMonthlyAllocatedAmount(expenseResponseDto.getExpense().getMonthlyAllocatedAmount())
                .withYearlyAllocatedAmount(expenseResponseDto.getExpense().getYearlyAllocatedAmount())
                .withNote(expenseResponseDto.getExpense().getNote())
                .withStatus(StatusMapper.toDomain(expenseResponseDto.getExpense().getStatus()))
                .withExpenseCategory(expenseCategoryMapper.convertToDomain(expenseResponseDto.getExpense().getExpenseCategory()))
                .build();
    }

    public Expense convertToDomain(ExpenseDto expenseDto){
        return Expense.builder()
                .withExpenseName(expenseDto.getExpenseName())
                .withMonthlyAllocatedAmount(expenseDto.getMonthlyAllocatedAmount())
                .withYearlyAllocatedAmount(expenseDto.getYearlyAllocatedAmount())
                .withNote(expenseDto.getNote())
                .withStatus(isNull(expenseDto.getStatus()) ? Status.ACTIVE : StatusMapper.toDomain(expenseDto.getStatus()))
                .withExpenseCategory(expenseCategoryMapper.convertToDomain(expenseDto.getExpenseCategory()))
                .build();
    }

    public Expense convertToDomainWithId(ExpenseDto expenseDto, Long id){
        return Expense.builder()
                .withId(id)
                .withExpenseName(expenseDto.getExpenseName())
                .withMonthlyAllocatedAmount(expenseDto.getMonthlyAllocatedAmount())
                .withYearlyAllocatedAmount(expenseDto.getYearlyAllocatedAmount())
                .withNote(expenseDto.getNote())
                .withStatus(isNull(expenseDto.getStatus()) ? Status.ACTIVE : StatusMapper.toDomain(expenseDto.getStatus()))
                .withExpenseCategory(expenseCategoryMapper.convertToDomain(expenseDto.getExpenseCategory()))
                .build();
    }
}
