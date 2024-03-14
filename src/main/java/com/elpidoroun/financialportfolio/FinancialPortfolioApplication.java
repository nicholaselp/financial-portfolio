package com.elpidoroun.financialportfolio;

import com.elpidoroun.financialportfolio.service.CreateExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PreDestroy;

@SpringBootApplication(scanBasePackages = {
		"com.elpidoroun.financialportfolio",
		"com.elpidoroun.financialportfolio.generated"
})
public class FinancialPortfolioApplication {

	private static final Logger logger = LoggerFactory.getLogger(CreateExpenseService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(FinancialPortfolioApplication.class, args);
	}

	@PreDestroy
	public void onExit(){
		final String DROP_TABLES = "DROP TABLE IF EXISTS public.expenses CASCADE; DROP TABLE IF EXISTS public.databasechangelog CASCADE; DROP TABLE IF EXISTS public.databasechangeloglock CASCADE";
		logger.warn("DROPPING ALL TABLES IN DATABASE...");
		jdbcTemplate.execute(DROP_TABLES);

	}

}
