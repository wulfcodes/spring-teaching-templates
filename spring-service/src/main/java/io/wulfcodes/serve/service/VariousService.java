package io.wulfcodes.serve.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VariousService {

    // =================================================================
    // 1. FIXED POOL USAGE ("fixed")
    // Scenario: CPU-Intensive Tasks
    // Why: We have 10 threads. If we get 50 requests, 40 wait in the queue.
    //      This prevents the CPU from thrashing/freezing by limiting concurrency.
    // =================================================================
    @Async("fixed")
    public void resizeImage(String imageId) {
        System.out.println("[Fixed] Resizing image " + imageId + " on " + Thread.currentThread().getName());

        // Simulating heavy math/CPU work
        double result = 0;
        for (int i = 0; i < 1_000_000; i++) {
            result += Math.sqrt(i) * Math.tan(i);
        }
    }

    // =================================================================
    // 2. CACHED POOL USAGE ("cached")
    // Scenario: Burst of Short-Lived Tasks
    // Why: We expect a spike of traffic (e.g., 500 webhooks at once).
    //      We want them processed NOW, not queued. The pool expands instantly
    //      and shrinks back to 0 when idle.
    // =================================================================
    @Async("cached")
    public void handleThirdPartyWebhook(String payload) {
        System.out.println("[Cached] Processing webhook on " + Thread.currentThread().getName());

        // Quick validation and DB save (Short duration)
        try { Thread.sleep(50); } catch (InterruptedException e) { }
    }

    // =================================================================
    // 3. SINGLE THREAD USAGE ("single")
    // Scenario: Strict Ordering (FIFO)
    // Why: We must write to the file in the exact order requests arrived.
    //      Parallel threads would jumble the log lines.
    // =================================================================
    @Async("single")
    public void appendAuditLog(String action) {
        System.out.println("[Single] Writing to file on " + Thread.currentThread().getName());

        // Simulating file write
        // Since this executor is single-threaded, we don't need 'synchronized' blocks here!
        try (FileWriter writer = new FileWriter("audit.log", true)) {
            writer.write(action + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =================================================================
    // 4. VIRTUAL THREADS USAGE ("virtual")
    // Scenario: High Throughput IO (Waiting)
    // Why: We need to send 10,000 emails. Standard threads would consume 10GB+ RAM.
    //      Virtual threads consume bytes. We can have 10k active "waiting" threads.
    // =================================================================
    @Async("virtual")
    public void sendMarketingEmail(String userEmail) {
        System.out.println("[Virtual] Sending email on " + Thread.currentThread().getName());

        // Simulating 2 seconds of Network Latency (Waiting)
        // Virtual threads unmount here, freeing the underlying OS thread for other work.
        try { Thread.sleep(2000); } catch (InterruptedException e) { }
    }


    // =================================================================
    // 5. WORK STEALING USAGE ("stealing")
    // Scenario: Recursive Divide-and-Conquer
    // Why: Processing a folder with sub-folders. The main task spawns sub-tasks.
    //      Idle threads "steal" sub-tasks from busy threads' queues.
    // =================================================================
    @Async("stealing")
    public CompletableFuture<Integer> countFilesRecursive(String path) {
        System.out.println("[Stealing] Scanning " + path + " on " + Thread.currentThread().getName());

        // In a real recursive scenario, you would manually fork tasks here.
        // But even for standard lists, this pool balances uneven loads well.
        return CompletableFuture.completedFuture(42);
    }
}
