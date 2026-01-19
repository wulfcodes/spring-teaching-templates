package io.wulfcodes.bank.service.impl;

import io.wulfcodes.bank.common.Constants;
import io.wulfcodes.bank.mapper.AccountMapper;
import io.wulfcodes.bank.model.dto.AccountData;
import io.wulfcodes.bank.model.po.Account;
import io.wulfcodes.bank.model.ro.AccountResponse;
import io.wulfcodes.bank.persistence.dao.AccountDao;
import io.wulfcodes.bank.service.spec.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Account Service Implementation.
 */
@Service
@Primary
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    // Field injection was requested, but Constructor is generally better.
    // However, user specifically asked for "inject using field injection, not
    // constructor or setter injection"
    // I MUST FOLLOW USER INSTRUCTION.
    // Wait, the user said: "inject using field injection, not constructor or setter
    // injection."
    // I will refactor to use field injection.

    // Using constructor here initially, I will remove it and use @Autowired on
    // fields.
    // Actually, I can fix it now before writing.

    /*
     * public AccountServiceImpl(AccountDao accountDao, AccountMapper accountMapper)
     * {
     * this.accountDao = accountDao;
     * this.accountMapper = accountMapper;
     * }
     */

    @org.springframework.beans.factory.annotation.Autowired
    private AccountDao accountDao;
    // Renamed to check if it works, actually I'll stick to variable names but use
    // @Autowired.

    @org.springframework.beans.factory.annotation.Autowired
    private AccountMapper accountMapper;

    @org.springframework.beans.factory.annotation.Autowired
    private org.springframework.context.ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    @Retryable(retryFor = { SQLException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public AccountResponse createAccount(AccountData accountData) {
        logger.info("Creating account for owner: {}", accountData.getOwnerName());
        Account account = accountMapper.toEntity(accountData);
        account.setAccountNumber(UUID.randomUUID().toString());
        Account savedAccount = accountDao.save(account);

        eventPublisher.publishEvent(new io.wulfcodes.bank.common.event.AccountCreatedEvent(
                savedAccount.getAccountNumber(), savedAccount.getOwnerName()));

        return accountMapper.toResponse(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    @org.springframework.cache.annotation.Cacheable(value = "accounts", key = "#accountNumber")
    public AccountResponse getAccount(String accountNumber) {
        logger.info("Fetching account: {}", accountNumber);
        return accountDao.findByAccountNumber(accountNumber)
                .map(accountMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with number: " + accountNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccounts() {
        return accountDao.findAll().stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(fixedRate = 60000) // Every minute
    public void auditAccounts() {
        // Log MDC might be missing in scheduled threads, so we might need to set it
        // manually if we care,
        // or just let it be empty/system default.
        MDC.put(Constants.MDC_CORRELATION_ID, "SYSTEM-SCHEDULED");
        logger.info("Running scheduled audit of accounts...");
        long count = accountDao.findAll().size();
        logger.info("Total accounts audited: {}", count);
        MDC.clear();
    }
}
