package io.wulfcodes.serve.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async("threadPool")
    public void processStandardWebTask(String userId) {
        System.out.println("[Standard] Processing user " + userId + " on " + Thread.currentThread().getName());
    }

    @Async("simpleAsync")
    public void runThrottledBackgroundJob() {
        System.out.println("[Simple] Running job on " + Thread.currentThread().getName());
    }
}
