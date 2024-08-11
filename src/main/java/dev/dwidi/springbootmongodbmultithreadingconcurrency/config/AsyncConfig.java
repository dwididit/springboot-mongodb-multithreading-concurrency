package dev.dwidi.springbootmongodbmultithreadingconcurrency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
/**
 * The AsyncConfig class provides a custom configuration for the asynchronous execution of tasks.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("UserThread-");
        executor.initialize();
        return executor;
    }
}
