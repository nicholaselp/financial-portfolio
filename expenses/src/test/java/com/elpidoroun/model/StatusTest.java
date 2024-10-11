package com.elpidoroun.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StatusTest {

    @Test
    public void get_value_test(){
        assertThat(Status.ACTIVE.getValue()).isEqualTo("active");
    }
}
