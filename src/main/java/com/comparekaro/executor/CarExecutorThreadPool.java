package com.comparekaro.executor;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class CarExecutorThreadPool {
    @Getter private ExecutorService executorService;
    @PostConstruct
    public void init(){
        executorService = new ThreadPoolExecutor(
                50,
                100,
                1000,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new CarExecutorThreadFactory("CarComparision")
        );
    }

    public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executorService);
    }

    private static class CarExecutorThreadFactory implements ThreadFactory {
        private String name;
        private int counter;

        public CarExecutorThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, name + " - Thread - " + counter++);
        }
    }

}
