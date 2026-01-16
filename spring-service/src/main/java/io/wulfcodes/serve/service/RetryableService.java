package io.wulfcodes.serve.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryableService {
    @Retryable(
        maxAttempts = 5,
        backoff = @Backoff(delay = 2000, multiplier = 2), // 2sec -> 4sec -> 8sec -> 16sec -> 32sec
        retryFor = { NullPointerException.class, ArithmeticException.class },
        noRetryFor = { IllegalArgumentException.class }
    )
    public void performTask() {
        throw new ArithmeticException();
    }

    @Retryable(
        maxAttempts = 1,
        retryFor = ArithmeticException.class,
        recover = "divide"
    )
    public float divide(int num1, int num2) {
        return num1 / num2;
    }

    @Recover
    public float divide(ArithmeticException ex, int num1, int num2) {
        System.out.println("Couldn't divide num2");
        return -1;
    }



}
