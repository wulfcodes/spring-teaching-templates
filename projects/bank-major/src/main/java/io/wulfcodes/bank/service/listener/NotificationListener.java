package io.wulfcodes.bank.service.listener;

import io.wulfcodes.bank.common.event.AccountCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Listener for Account notifications.
 */
@Component
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);

    @Async
    @EventListener
    public void handleAccountCreatedEvent(AccountCreatedEvent event) {
        logger.info("Sending welcome notification to owner: {} for account: {}", event.ownerName(),
                event.accountNumber());
        // Simulate email sending logic
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Notification sent successfully.");
    }
}
