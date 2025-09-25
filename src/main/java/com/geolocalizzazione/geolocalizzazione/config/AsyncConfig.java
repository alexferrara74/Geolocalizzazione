package com.geolocalizzazione.geolocalizzazione.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);       // min thread
        executor.setMaxPoolSize(10);       // max thread
        executor.setQueueCapacity(50);     // coda prima di creare nuovi thread
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}
