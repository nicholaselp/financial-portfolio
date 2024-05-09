package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.exceptions.UnauthorizedException;
import com.elpidoroun.financialportfolio.generated.dto.BillingIntervalDto;
import com.elpidoroun.financialportfolio.generated.dto.ExpenseCategoryDto;
import com.elpidoroun.financialportfolio.mappers.ExpenseCategoryMapper;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseCategoryRepository;
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
import static com.elpidoroun.financialportfolio.model.ExpenseCategoryTestFactory.createExpenseCategoryDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class ExpenseCategoryApiControllerDelegateTest extends ExpenseCategorySpringTestHelper {

    @Autowired private ExpenseCategoryRepository expenseCategoryRepository;
    @Autowired private ExpenseCategoryMapper expenseCategoryMapper;
    @Nested
    class AuthenticationTests {

        @ParameterizedTest
        @MethodSource("admin_only_api_calls")
        void fail_unauthorized_user(String endpoint, HttpMethod httpMethod) throws Exception {
            MvcResult result = mockMvc().perform(resolveRequestMethod(httpMethod, endpoint)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateUserToken())
                            .content(objectMapper.writeValueAsString(createExpenseCategoryDto())))
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andReturn();

            assertErrorResponse(result, "You do not have Permission to access this API", "Forbidden");
        }

        public static Stream<Arguments> admin_only_api_calls() {
            return Stream.of(
                    Arguments.of(CREATE_EXPENSE_CATEGORY_POST_ENDPOINT, HttpMethod.POST),
                    Arguments.of(UPDATE_EXPENSE_CATEGORY_PUT_ENDPOINT, HttpMethod.PUT),
                    Arguments.of(DELETE_EXPENSE_CATEGORY_DELETE_ENDPOINT + "/1", HttpMethod.DELETE)
            );
        }

        @ParameterizedTest
        @MethodSource("all_APIs")
        public void fail_no_authentication(String endpoint, HttpMethod httpMethod) {
            assertThatThrownBy(() -> mockMvc().perform(resolveRequestMethod(httpMethod, endpoint)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createExpenseCategoryDto())))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andReturn())
                    .hasMessage("Authorization token is missing")
                    .isInstanceOf(UnauthorizedException.class);
        }

        public static Stream<Arguments> all_APIs() {
            return Stream.of(
                    Arguments.of(CREATE_EXPENSE_CATEGORY_POST_ENDPOINT, HttpMethod.POST),
                    Arguments.of(GET_EXPENSE_CATEGORY_BY_ID_GET_ENDPOINT + "/1", HttpMethod.GET),
                    Arguments.of(GET_ALL_EXPENSE_CATEGORIES_GET_ENDPOINT, HttpMethod.GET),
                    Arguments.of(UPDATE_EXPENSE_CATEGORY_PUT_ENDPOINT + "/1", HttpMethod.PUT),
                    Arguments.of(DELETE_EXPENSE_CATEGORY_DELETE_ENDPOINT + "/1", HttpMethod.DELETE)
            );
        }
    }

    @Nested
    class CreateExpenseCategoryTests {
        @Test
        public void success() throws Exception {
            var request = createExpenseCategoryDto();

            MvcResult result = mockMvc().perform(post("/v1/expense-category")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken())
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var extractResponse = extractResponse(result, ExpenseCategoryDto.class);

            var domainEntity = expenseCategoryRepository.findById(extractResponse.getId());

            assertThat(domainEntity).isPresent();
            assertRequestResponse(request, extractResponse);
            assertResponseWithDomain(extractResponse, domainEntity.get());
        }

        @Test
        public void fail() throws Exception {
            expenseCategoryRepository.save(createExpenseCategory("categoryName"));

            MvcResult result = mockMvc().perform(post("/v1/expense-category")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken())
                            .content(objectMapper.writeValueAsString(createExpenseCategoryDto("categoryName"))))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn();

            assertErrorResponse(result, "Expense Category with name: categoryName already exists", "Validation error occurred");

        }
    }

    @Nested
    class UpdateExpenseCategoryTest {
        @Test
        public void success() throws Exception {
            var original = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
            var request = expenseCategoryMapper.convertToDto(original);
            request.setBillingInterval(BillingIntervalDto.YEARLY);

            var updated = expenseCategoryRepository.findById(original.getId());
            assertThat(updated).isPresent()
                    .hasValueSatisfying(expenseCategory -> {
                        assertThat(expenseCategory.getBillingInterval()).isEqualTo(original.getBillingInterval());
                    });

            MvcResult result = mockMvc().perform(put("/v1/expense-category")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken())
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var extractResponse = extractResponse(result, ExpenseCategoryDto.class);

            var domainEntity = expenseCategoryRepository.findById(extractResponse.getId());

            assertThat(domainEntity).isPresent();
            assertRequestResponse(request, extractResponse);
            assertResponseWithDomain(extractResponse, domainEntity.get());
        }

        @Test
        public void fail() throws Exception {
            var original = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory());
            var request = expenseCategoryMapper.convertToDto(original);
            request.setCategoryName("updated");

            var updated = expenseCategoryRepository.findById(original.getId());
            assertThat(updated).isPresent()
                    .hasValueSatisfying(expenseCategory -> {
                        assertThat(expenseCategory.getCategoryName()).isEqualTo(original.getCategoryName());
                    });

            MvcResult result = mockMvc().perform(put("/v1/expense-category")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken())
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn();

            assertErrorResponse(result, "Cannot update Expense Category Name", "Validation error occurred");
        }
    }

    @Nested
    class GetExpenseCategoryByIdTest {
        @Test
        public void success() throws Exception {
            var storedExpenseCategoryId = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory()).getId();

            MvcResult result = mockMvc().perform(get("/v1/expense-category/" + storedExpenseCategoryId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var extractResponse = extractResponse(result, ExpenseCategoryDto.class);
            var domainEntity = expenseCategoryRepository.findById(extractResponse.getId());

            assertThat(domainEntity).isPresent();
            assertResponseWithDomain(extractResponse, domainEntity.get());
        }

        @Test
        public void fail() throws Exception {
            MvcResult result = mockMvc().perform(get("/v1/expense-category/5")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn();

            assertErrorResponse(result, "Expense Category with ID: 5 not found", "Entity Not Found");
        }
    }

    @Nested
    class GetAllExpenseCategories {
        @Test
        public void success() throws Exception {
            expenseCategoryRepository.save(createExpenseCategory("name1"));
            expenseCategoryRepository.save(createExpenseCategory("name2"));

            MvcResult result = mockMvc().perform(get("/v1/expense-category")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var response = extractToList(result, ExpenseCategoryDto.class);

            List<ExpenseCategory> domainEntities = expenseCategoryRepository.findAll();

            assertThat(domainEntities).isNotEmpty().hasSize(2);
            assertThat(domainEntities).hasSameSizeAs(response);
            assertThat(domainEntities)
                    .containsExactlyInAnyOrderElementsOf(
                            response.stream()
                                    .map(expenseCategoryMapper::convertToDomain).
                                    collect(Collectors.toList()));
        }

        @Test
        public void fail() throws Exception {
            MvcResult result = mockMvc().perform(get("/v1/expense-category")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            List<ExpenseCategoryDto> response = extractToList(result, ExpenseCategoryDto.class);
            assertThat(response).isNotNull().hasSize(0);
        }
    }

    @Nested
    class DeleteExpenseCategoryById {
        @Test
        public void success() throws Exception {
            var storedExpenseCategoryId = expenseCategoryRepository.save(ExpenseCategoryTestFactory.createExpenseCategory()).getId();

            MvcResult result = mockMvc().perform(delete("/v1/expense-category/" + storedExpenseCategoryId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var domainEntity = expenseCategoryRepository.findById(storedExpenseCategoryId);

            assertThat(domainEntity).isNotPresent();
        }

        @Test
        public void fail() throws Exception {

            MvcResult result = mockMvc().perform(delete("/v1/expense-category/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + generateAdminToken()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn();

            assertErrorResponse(result, "Expense Category with ID: 1 not found. Nothing will be deleted", "Invalid Argument");
        }
    }
}

