package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.generated.dto.ExpenseResponseDto;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.elpidoroun.financialportfolio.model.ExpenseTestFactory.createExpenseDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class ExpenseApiControllerDelegateTest extends SpringTests{

    @Autowired private MockMvc mockMvc;
    @Autowired private ExpenseRepository expenseRepository;

    @Test
    public void create_expense_api_call() throws Exception {
        var expenseDto = createExpenseDto();

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var response = extractResponse(result, ExpenseResponseDto.class);

        assertThat(response.getExpense().getExpenseName()).isEqualTo(expenseDto.getExpenseName());
    }

    @Test
    public void update_expense_api_call() throws Exception {
        var storedExpense = expenseRepository.save(ExpenseTestFactory.createExpense());

        assertThat(expenseRepository.findById(storedExpense.getId())).isPresent()
                .hasValueSatisfying(exp -> {
                    assertThat(exp.getExpenseName()).isEqualTo("rent");
                });

        var updateExpenseDto = ExpenseTestFactory.createExpenseDto();
        updateExpenseDto.setExpenseName("updatedExpense");

        var result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/expenses/"+storedExpense.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateExpenseDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var response = extractResponse(result, ExpenseResponseDto.class);

        assertThat(response.getExpense().getExpenseName()).isEqualTo(updateExpenseDto.getExpenseName());
    }

    @Test
    public void get_expense_by_id_api_call() throws Exception {
        var storedExpense = expenseRepository.save(ExpenseTestFactory.createExpense());

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/expenses/"+storedExpense.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var response = extractResponse(result, ExpenseResponseDto.class);

        assertThat(response.getExpense().getExpenseName()).isEqualTo(storedExpense.getExpenseName());
    }

    @Test
    public void delete_expense_by_id_api_call() throws Exception {
        var storedExpense = expenseRepository.save(ExpenseTestFactory.createExpense());

        var result = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/expenses/"+storedExpense.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEmpty();
        assertThat(expenseRepository.findAll()).isEmpty();
    }

}
