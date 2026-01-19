package io.wulfcodes.bank.common.event;

/**
 * Event published when an account is created.
 */
public record AccountCreatedEvent(String accountNumber, String ownerName) {
}
