package com.elpidoroun.model;

import org.junit.jupiter.api.Test;

import static com.elpidoroun.model.Currency.EURO;
import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyTest {

    @Test
    public void get_value_test(){
        assertThat(EURO.getValue()).isEqualTo("EUR");
    }
}