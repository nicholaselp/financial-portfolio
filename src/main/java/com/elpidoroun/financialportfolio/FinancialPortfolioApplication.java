package com.elpidoroun.financialportfolio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.elpidoroun.financialportfolio",
		"com.elpidoroun.financialportfolio.generated"
})
public class FinancialPortfolioApplication {

	private static final Logger logger = LoggerFactory.getLogger(FinancialPortfolioApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FinancialPortfolioApplication.class, args);
		logger.info("\n\n***************************************************\n" +
				"***                                             ***\n" +
				"***   🚀 APPLICATION STARTED SUCCESSFULLY 🚀   ***\n" +
				"***                                             ***\n" +
				"***************************************************\n\n");

	}

	/** If you want to drop all tables during shutdown of application (Must Autowire JdbcTemplate) **/
//	@PreDestroy
//	public void onExit(){
//		final String DROP_TABLES = "DROP TABLE IF EXISTS public.expenses CASCADE; DROP TABLE IF EXISTS public.databasechangelog CASCADE; DROP TABLE IF EXISTS public.databasechangeloglock CASCADE";
//		logger.warn("DROPPING ALL TABLES IN DATABASE...");
//		jdbcTemplate.execute(DROP_TABLES);
//
//	}

}
