package com.elpidoroun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication(scanBasePackages = {
        "com.elpidoroun",
        "com.elpidoroun.generated"
})
@EnableCaching
public class ExpenseServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ExpenseServiceApplication.class);
        app.run(args);
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> log.info("\n\n" +
                "*******************************************************\n" +
                "***                                                 ***\n" +
                "***   🚀 EXPENSE SERVICE STARTED SUCCESSFULLY 🚀    ***\n" +
                "***                                                 ***\n" +
                "*******************************************************" +
                "\n\n"), 3, TimeUnit.SECONDS);
        scheduler.shutdown();
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