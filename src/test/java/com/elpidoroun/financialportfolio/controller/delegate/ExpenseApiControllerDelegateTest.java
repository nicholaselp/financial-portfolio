package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.exceptions.UnauthorizedException;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseMapper;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory.createExpenseCategory;
import static com.elpidoroun.financialportfolio.model.ExpenseTestFactory.createExpense;
import static com.elpidoroun.financialportfolio.model.ExpenseTestFactory.createExpenseDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class ExpenseApiControllerDelegateTest extends ExpenseSpringTestHelper {

    @Autowired private ExpenseRepository expenseRepository;
    @Autowired private ExpenseCategoryRepository expenseCategoryRepository;
    @Autowired private ExpenseMapper expenseMapper;
    @Nested
    class AuthenticationTests {

        @ParameterizedTest
        @MethodSource("all_APIs")
        public void fail_no_authentication(String endpoint, HttpMethod httpMethod) {
            assertThatThrownBy(() -> mockMvc().perform(resolveRequestMethod(httpMethod, endpoint)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createExpenseDto())))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andReturn())
                    .hasMessage("Authorization token is missing")
                    .isInstanceOf(UnauthorizedException.class);
        }

        public static Stream<Arguments> all_APIs() {
            return Stream.of(
                    Arguments.of(CREATE_EXPENSE_POST_ENDPOINT, HttpMethod.POST),
                    Arguments.of(GET_EXPENSE_BY_ID_GET_ENDPOINT + "/1", HttpMethod.GET),
                    Arguments.of(GET_ALL_EXPENSE_CATEGORIES_GET_ENDPOINT, HttpMethod.GET),
                    Arguments.of(UPDATE_EXPENSE_PUT_ENDPOINT + "/1", HttpMethod.PUT),
                    Arguments.of(DELETE_EXPENSE_DELETE_ENDPOINT + "/1", HttpMethod.DELETE)
            );
        }
    }

    @Nested
    class CreateExpenseTests {
        @Test
        public void success() throws Exception {
            var request = createExpenseDto("expense",
                    expenseCategoryMapper.convertToDto(
                            expenseCategoryRepository.save(createExpenseCategory())));

            MvcResult result = mockMvc().perform(post("/v1/expense")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken())
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var extractResponse = extractResponse(result, ExpenseResponseDto.class);

            var domainEntity = expenseRepository.findById(extractResponse.getMeta().getId());

            assertThat(domainEntity).isPresent();
            assertRequestResponse(request, extractResponse);
            assertResponseWithDomain(extractResponse, domainEntity.get());
        }

        @Test
        public void fail() throws Exception {
            var expenseCategoryDto = expenseCategoryRepository.save(createExpenseCategory());
            var expenseDto = expenseMapper.convertToDto(
                    expenseRepository.save(createExpense("expenseName", expenseCategoryDto)));

            MvcResult result = mockMvc().perform(post("/v1/expense")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken())
                            .content(objectMapper.writeValueAsString(expenseDto)))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn();

            assertErrorResponse(result, "Expense with name: expenseName already exists", "Validation error occurred");

        }
    }

    @Nested
    class UpdateExpenseTest {
        @Test
        public void success() throws Exception {
            var expenseCategory = expenseCategoryRepository.save(createExpenseCategory());
            var original = expenseRepository.save(createExpense(expenseCategory));
            var request = expenseMapper.convertToDto(original);
            request.setExpenseName("updateExpenseName");
            
            var updated = expenseRepository.findById(original.getId());
            assertThat(updated).isPresent()
                    .hasValueSatisfying(Expense -> {
                        assertThat(Expense.getExpenseName()).isEqualTo(original.getExpenseName());
                    });

            MvcResult result = mockMvc().perform(put("/v1/expense/" + original.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken())
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var extractResponse = extractResponse(result, ExpenseResponseDto.class);

            var domainEntity = expenseRepository.findById(extractResponse.getMeta().getId());

            assertThat(domainEntity).isPresent();
            assertRequestResponse(request, extractResponse);
            assertResponseWithDomain(extractResponse, domainEntity.get());
        }

        @Test
        public void fail() throws Exception {
            var request = createExpenseDto();
            request.setExpenseName("updated");

            MvcResult result = mockMvc().perform(put("/v1/expense/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken())
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn();

            assertErrorResponse(result, "Expense with ID: 1 not found", "Entity Not Found");
        }
    }

    @Nested
    class GetExpenseByIdTest {
        @Test
        public void success() throws Exception {
            var storedExpenseId = expenseRepository.save(createExpense(expenseCategoryRepository.save(createExpenseCategory()))).getId();

            MvcResult result = mockMvc().perform(get("/v1/expense/" + storedExpenseId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var extractResponse = extractResponse(result, ExpenseResponseDto.class);
            var domainEntity = expenseRepository.findById(extractResponse.getMeta().getId());

            assertThat(domainEntity).isPresent();
            assertResponseWithDomain(extractResponse, domainEntity.get());
        }

        @Test
        public void fail() throws Exception {
            MvcResult result = mockMvc().perform(get("/v1/expense/5")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn();

            assertErrorResponse(result, "Expense with ID: 5 not found", "Entity Not Found");
        }
    }

    @Nested
    class GetAllExpenses {
        @Test
        public void success() throws Exception {
            var storedExpenseCategory = expenseCategoryRepository.save(createExpenseCategory());

            expenseRepository.save(createExpense("name1", storedExpenseCategory));
            expenseRepository.save(createExpense("name2", storedExpenseCategory));

            MvcResult result = mockMvc().perform(get("/v1/expense")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var response = extractToList(result, ExpenseResponseDto.class);

            List<Expense> domainEntities = expenseRepository.findAll();

            assertThat(domainEntities).isNotEmpty().hasSize(2);
            assertThat(domainEntities).hasSameSizeAs(response);
            assertThat(domainEntities)
                    .containsExactlyInAnyOrderElementsOf(
                            response.stream()
                                    .map(expenseMapper::convertToDomain).
                                    collect(Collectors.toList()));
        }

        @Test
        public void fail() throws Exception {
            MvcResult result = mockMvc().perform(get("/v1/expense")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            List<ExpenseDto> response = extractToList(result, ExpenseDto.class);
            assertThat(response).isNotNull().hasSize(0);
        }
    }

    @Nested
    class DeleteExpenseById {
        @Test
        public void success() throws Exception {
            var storedExpenseId = expenseRepository.save(createExpense(expenseCategoryRepository.save(createExpenseCategory()))).getId();

            MvcResult result = mockMvc().perform(delete("/v1/expense/" + storedExpenseId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
//                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var domainEntity = expenseRepository.findById(storedExpenseId);

            assertThat(domainEntity).isNotPresent();
        }

        @Test
        public void fail() throws Exception {

            MvcResult result = mockMvc().perform(delete("/v1/expense/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn();

            assertErrorResponse(result, "Expense with ID: 1 not found. Nothing will be deleted", "Entity Not Found");
        }
    }
}

