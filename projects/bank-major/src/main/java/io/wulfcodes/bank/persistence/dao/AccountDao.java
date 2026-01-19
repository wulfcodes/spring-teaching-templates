package io.wulfcodes.bank.persistence.dao;

import io.wulfcodes.bank.model.po.Account;
import io.wulfcodes.bank.persistence.repo.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Account Data Access Object.
 */
@Component
public class AccountDao {

    private final AccountRepository accountRepository;

    public AccountDao(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
