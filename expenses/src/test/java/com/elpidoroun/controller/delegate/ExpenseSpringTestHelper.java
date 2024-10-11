package com.elpidoroun.controller.delegate;

import com.elpidoroun.generated.dto.ExpenseDto;
import com.elpidoroun.generated.dto.ExpenseResponseDto;
import com.elpidoroun.mappers.ExpenseCategoryMapper;
import com.elpidoroun.mappers.StatusMapper;
import com.elpidoroun.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class ExpenseSpringTestHelper extends SpringTestsHelper {

    public static final String CREATE_EXPENSE_POST_ENDPOINT = "/v1/expense";
    public static final String UPDATE_EXPENSE_PUT_ENDPOINT = "/v1/expense";
    public static final String GET_EXPENSE_BY_ID_GET_ENDPOINT = "/v1/expense";
    public static final String GET_ALL_EXPENSE_CATEGORIES_GET_ENDPOINT = "/v1/expense";
    public static final String DELETE_EXPENSE_DELETE_ENDPOINT = "/v1/expense";

    @Autowired ExpenseCategoryMapper expenseCategoryMapper;

    protected void assertRequestResponse(ExpenseDto requestDto, ExpenseResponseDto responseDto) {
        assertThat(requestDto.getExpenseName()).isEqualTo(responseDto.getExpense().getExpenseName());
        assertThat(requestDto.getStatus()).isEqualTo(responseDto.getExpense().getStatus());
        assertThat(requestDto.getMonthlyAllocatedAmount()).isEqualTo(responseDto.getExpense().getMonthlyAllocatedAmount());
        assertThat(requestDto.getYearlyAllocatedAmount()).isEqualTo(responseDto.getExpense().getYearlyAllocatedAmount());
        assertThat(requestDto.getNote()).isEqualTo(responseDto.getExpense().getNote());
        assertThat(requestDto.getExpenseCategory()).isEqualTo(responseDto.getExpense().getExpenseCategory());
    }

    protected void assertRequestResponse(ExpenseResponseDto requestDto, ExpenseResponseDto responseDto) {
        assertThat(requestDto.getExpense().getExpenseName()).isEqualTo(responseDto.getExpense().getExpenseName());
        assertThat(requestDto.getExpense().getStatus()).isEqualTo(responseDto.getExpense().getStatus());
        assertThat(requestDto.getExpense().getMonthlyAllocatedAmount()).isEqualTo(responseDto.getExpense().getMonthlyAllocatedAmount());
        assertThat(requestDto.getExpense().getYearlyAllocatedAmount()).isEqualTo(responseDto.getExpense().getYearlyAllocatedAmount());
        assertThat(requestDto.getExpense().getNote()).isEqualTo(responseDto.getExpense().getNote());
        assertThat(requestDto.getExpense().getExpenseCategory()).isEqualTo(responseDto.getExpense().getExpenseCategory());
    }

    protected void assertResponseWithDomain(ExpenseResponseDto responseDto, Expense expense) {
        assertThat(expense.getExpenseName()).isEqualTo(responseDto.getExpense().getExpenseName());
        assertThat(expense.getStatus()).isEqualTo(StatusMapper.toDomain(responseDto.getExpense().getStatus()));
        assertThat(expense.getMonthlyAllocatedAmount()).isEqualTo(responseDto.getExpense().getMonthlyAllocatedAmount());
        assertThat(expense.getYearlyAllocatedAmount()).isEqualTo(responseDto.getExpense().getYearlyAllocatedAmount());
        expense.getNote().ifPresent(note -> {
            assertThat(note).isEqualTo(responseDto.getExpense().getNote());
        });
        assertThat(expense.getExpenseCategory()).isEqualTo(expenseCategoryMapper.convertToDomain(responseDto.getExpense().getExpenseCategory()));
        assertThat(expense.getId()).isEqualTo(responseDto.getMeta().getId());
        assertThat(expense.getCreatedAt()).isEqualTo(responseDto.getMeta().getCreatedAt());
    }
    public MockHttpServletRequestBuilder resolveRequestMethod(HttpMethod httpMethod, String endpointUrl) {
        if (httpMethod.equals(POST)) {
            return post(endpointUrl);
        } else if (httpMethod.equals(GET)) {
            return get(endpointUrl);
        } else if (httpMethod.equals(PUT)) {
            return put(endpointUrl);
        } else if (httpMethod.equals(DELETE)) {
            return delete(endpointUrl);
        }
        throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
    }
}
