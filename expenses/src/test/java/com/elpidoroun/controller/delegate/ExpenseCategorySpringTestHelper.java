package com.elpidoroun.controller.delegate;

import com.elpidoroun.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.mappers.BillingIntervalMapper;
import com.elpidoroun.mappers.ExpenseTypeMapper;
import com.elpidoroun.model.ExpenseCategory;
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

public class ExpenseCategorySpringTestHelper extends SpringTestsHelper {

    public static final String CREATE_EXPENSE_CATEGORY_POST_ENDPOINT = "/v1/expense-category";
    public static final String UPDATE_EXPENSE_CATEGORY_PUT_ENDPOINT = "/v1/expense-category";
    public static final String GET_EXPENSE_CATEGORY_BY_ID_GET_ENDPOINT = "/v1/expense-category";
    public static final String GET_ALL_EXPENSE_CATEGORIES_GET_ENDPOINT = "/v1/expense-category";
    public static final String DELETE_EXPENSE_CATEGORY_DELETE_ENDPOINT = "/v1/expense-category";

    protected void assertRequestResponse(ExpenseCategoryDto request, ExpenseCategoryDto extractResponse) {
        assertThat(request.getCategoryName()).isEqualTo(extractResponse.getCategoryName());
        assertThat(request.getExpenseType()).isEqualTo(extractResponse.getExpenseType());
        assertThat(request.getBillingInterval()).isEqualTo(extractResponse.getBillingInterval());
    }

    protected void assertResponseWithDomain(ExpenseCategoryDto response, ExpenseCategory expenseCategory) {
        assertThat(response.getId()).isEqualTo(expenseCategory.getId());
        assertThat(response.getCategoryName()).isEqualTo(expenseCategory.getCategoryName());
        assertThat(response.getBillingInterval()).isEqualTo(BillingIntervalMapper.toDto(expenseCategory.getBillingInterval()));
        assertThat(response.getExpenseType()).isEqualTo(ExpenseTypeMapper.toDto(expenseCategory.getExpenseType()));
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
