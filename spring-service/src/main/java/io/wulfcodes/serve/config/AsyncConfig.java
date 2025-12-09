package io.wulfcodes.serve.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return threadPoolTaskExecutor();
    }

    // 1. STANDARD SPRING POOL (ThreadPoolTaskExecutor)
    // What it is: Spring's wrapper around Java's ThreadPoolExecutor.
    // Use-Case: The default "General Purpose" pool. Safe, configurable, and robust.
    // Difference: It creates a Thread Pool. It keeps threads alive to reuse them.
    @Bean("threadPool")
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);    // Keep 10 threads alive
        executor.setMaxPoolSize(50);     // Burst up to 50 if busy
        executor.setQueueCapacity(1000); // Queue 1000 tasks before rejecting
        executor.setThreadNamePrefix("task-");
        executor.initialize();
        return executor;
    }

    // 2. SIMPLE EXECUTOR (SimpleAsyncTaskExecutor)
    // What it is: A Spring executor that is NOT a thread pool.
    // Use-Case: Prototyping or environments where you don't want to manage a pool.
    // Difference: It creates a NEW thread for every task (similar to perTask),
    @Bean("simpleAsync")
    public TaskExecutor simpleAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("SpringSimple-");
        executor.setConcurrencyLimit(100); // Allow max 100 active threads
        return executor;
    }

    // 1. FIXED POOL
    // What it is: A stable team of 10 workers. If 11 tasks arrive, the 11th waits.
    // Use-Case: CPU Protection. Use for heavy calculations to prevent server freeze.
    @Bean("fixed")
    public TaskExecutor fixedTaskExecutor() {
        ThreadFactory factory = new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("fixed-" + count.getAndIncrement());
                thread.setPriority(Thread.NORM_PRIORITY);
                thread.setDaemon(false); // Critical task, don't kill on shutdown
                return thread;
            }
        };

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10, factory);
        return new TaskExecutorAdapter(fixedThreadPool);
    }

    // 2. CACHED POOL
    // What it is: An elastic team that hires workers instantly and fires them when idle.
    // Use-Case: Traffic Spikes. Use for sudden bursts of small tasks (like webhooks).
    @Bean("cached")
    public TaskExecutor cachedTaskExecutor() {
        ThreadFactory factory = new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("cached-" + count.getAndIncrement());
                thread.setPriority(Thread.MIN_PRIORITY); // Low priority, background work
                thread.setDaemon(true); // Daemon: Kill these if the app wants to exit
                return thread;
            }
        };

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(factory);
        return new TaskExecutorAdapter(cachedThreadPool);
    }

    // 3. SINGLE THREAD
    // What it is: Exactly one worker. Guarantees Task A finishes before Task B starts.
    // Use-Case: Strict Order. Use for writing logs or sequential events.
    @Bean("single")
    public TaskExecutor singleTaskExecutor() {
        ThreadFactory factory = new ThreadFactory() {
            // Even though it's single, we number it to see restarts if it crashes
            private final AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("strict-" + count.getAndIncrement());
                t.setPriority(Thread.MAX_PRIORITY); // Highest priority
                return t;
            }
        };

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(factory);
        return new TaskExecutorAdapter(singleThreadExecutor);
    }

    // 4. VIRTUAL THREADS
    // What it is: Millions of ultra-lightweight threads managed by Java.
    // Use-Case: High Volume Waiting. Use for 10k+ tasks waiting for IO (DB, API).
    @Bean("virtual")
    public TaskExecutor virtualTaskExecutor() {
        ThreadFactory factory = Thread.ofVirtual()
                                      .name("virtual-", 0) // Start counting at 0
                                      .uncaughtExceptionHandler((t, e) -> System.err.println("Virtual Error: " + e.getMessage()))
                                      .factory();

        ExecutorService virtualThreadExecutor = Executors.newThreadPerTaskExecutor(factory);
        return new TaskExecutorAdapter(virtualThreadExecutor);
    }


    // 5. WORK STEALING
    // What it is: A smart pool where idle threads "steal" work from busy threads.
    // Use-Case: Recursion. Use for algorithms that split into sub-tasks.
    @Bean("stealing")
    public TaskExecutor stealingTaskExecutor() {
        Executor nativeExecutor = Executors.newWorkStealingPool();
        return new TaskExecutorAdapter(nativeExecutor);
    }
}