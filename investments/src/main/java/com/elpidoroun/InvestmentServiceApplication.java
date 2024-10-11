package com.elpidoroun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
@SpringBootApplication()
public class InvestmentServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(InvestmentServiceApplication.class);
        app.run(args);
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> log.info("\n\n***************************************************\n" +
                "***                                             ***\n" +
                "***   🚀 INVESTMENT SERVICE STARTED SUCCESSFULLY 🚀   ***\n" +
                "***                                             ***\n" +
                "***************************************************\n\n"), 3, TimeUnit.SECONDS);
        scheduler.shutdown();
    }
}