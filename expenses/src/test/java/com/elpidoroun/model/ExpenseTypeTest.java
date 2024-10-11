package com.elpidoroun.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseTypeTest {

    @Test
    public void get_value_test(){
        assertThat(ExpenseType.FIXED.getValue()).isEqualTo("fixed");
    }
}