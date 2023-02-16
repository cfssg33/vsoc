package com.autocrypt.mon.account;

import com.autocrypt.mon.account.dto.AccountDto;
import com.autocrypt.mon.account.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public List<AccountDto.AccountResponse> getAccountList() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDto.AccountResponse> accountDtoList = new ArrayList<>();
        for (Account account : accountList) {
            accountDtoList.add(new AccountDto.AccountResponse(account));
        }
        return accountDtoList;
    }

    @Transactional
    public AccountDto.AccountResponse createAccount(AccountDto.AccountCreateRequest accountCreateRequest) {
        String encPassword = new BCryptPasswordEncoder().encode(accountCreateRequest.getPassword());

        Account account = Account.builder().accountId(accountCreateRequest.getLoginId())
                .name(accountCreateRequest.getName())
                .password(encPassword)
                .createdDate(LocalDateTime.now())
                .lastAccessTime(LocalDateTime.now())
                .logCheckTime(null)
                .role("ROLE_ADMIN")
                .build();
        return new AccountDto.AccountResponse(accountRepository.save(account));
    }

    public AccountDto.AccountResponse getCurrentAccountInfo(HttpServletRequest request) {
        Account accountInfo = (Account)request.getSession().getAttribute("account");
        return new AccountDto.AccountResponse(accountInfo);
    }

    @Transactional
    public AccountDto.AccountLoginSuccessResponse getAccountLoginSuccessResponse(String loginId) {
        Account accountInfo = accountRepository.findByAccountId(loginId);
        AccountDto.AccountLoginSuccessResponse accountLoginSuccessResponse = new AccountDto.AccountLoginSuccessResponse(accountInfo);

        Account account = Account.builder().accountId(accountInfo.getAccountId())
                                                    .name(accountInfo.getName())
                                                    .password(accountInfo.getPassword())
                                                    .createdDate(accountInfo.getCreatedDate())
                                                    .lastAccessTime(LocalDateTime.now())
                                                    .logCheckTime(null)
                                                    .role(accountInfo.getRole())
                                                    .build();

        accountRepository.save(account);

        return accountLoginSuccessResponse;
    }
}
