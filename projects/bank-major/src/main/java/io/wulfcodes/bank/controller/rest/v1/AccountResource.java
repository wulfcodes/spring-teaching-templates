package io.wulfcodes.bank.controller.rest.v1;

import io.wulfcodes.bank.common.Constants;
import io.wulfcodes.bank.model.dto.AccountData;
import io.wulfcodes.bank.model.ro.AccountResponse;
import io.wulfcodes.bank.model.ro.ApiResponse;
import io.wulfcodes.bank.service.spec.AccountService;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * Account REST Resource.
 */
@RestController
@RequestMapping(value = "/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountData accountData) {
        AccountResponse response = accountService.createAccount(accountData);
        return ResponseEntity
                .created(URI.create(Constants.API_V1_PATH + "/accounts/" + response.getAccountNumber()))
                .body(ApiResponse.success(response, "Account created successfully"));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        List<AccountResponse> responses = accountService.getAllAccounts();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
