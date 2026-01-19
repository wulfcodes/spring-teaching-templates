package io.wulfcodes.bank.service.spec;

import io.wulfcodes.bank.model.dto.AccountData;
import io.wulfcodes.bank.model.ro.AccountResponse;

import java.util.List;

/**
 * Account Service Interface.
 */
public interface AccountService {

    AccountResponse createAccount(AccountData accountData);

    AccountResponse getAccount(String accountNumber);

    List<AccountResponse> getAllAccounts();

    void auditAccounts(); // Scheduled task demo
}
