package com.elpidoroun.model;

import org.junit.jupiter.api.Test;

import static com.elpidoroun.model.BillingInterval.MONTHLY;
import static org.assertj.core.api.Assertions.assertThat;

public class BillingIntervalTest {

    @Test
    public void get_value_test(){
        assertThat(MONTHLY.getValue()).isEqualTo("monthly");
    }
}