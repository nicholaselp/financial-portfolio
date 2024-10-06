//package com.elpidoroun.config;
//
//import liquibase.integration.spring.SpringLiquibase;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Primary
//    @Bean(name = "expensesDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource expensesDataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:postgresql://expenses_postgresql:5432/expenses")
//                .username("nicholaselp")
//                .password("password")
//                .driverClassName("org.postgresql.Driver")
//                .build();
//    }
//
//    @Bean
//    public SpringLiquibase liquibase(@Qualifier("expensesDataSource") DataSource expensesDataSource) {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(expensesDataSource);
//        liquibase.setChangeLog("classpath:liquibase/changelog/changelog-master.xml");
//        return liquibase;
//    }
//}
