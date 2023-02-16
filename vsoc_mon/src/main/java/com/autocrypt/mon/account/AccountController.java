package com.autocrypt.mon.account;

import com.autocrypt.mon.account.dto.AccountDto;
import com.autocrypt.mon.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {
    @Autowired AccountService accountService;

    // Returns a list of all user information
    @ResponseBody
    @GetMapping("/account")
    public List<AccountDto.AccountResponse> getAccountList() {
        return accountService.getAccountList();
    }

    // Returns information created after user account creation
    @ResponseBody
    @PostMapping("/account")
    public AccountDto.AccountResponse createAccount(@RequestBody AccountDto.AccountCreateRequest accountCreateRequest) {
        return accountService.createAccount(accountCreateRequest);
    }

    // Return current user account
    @ResponseBody
    @GetMapping("/account/currentInfo")
    public AccountDto.AccountResponse getAccountInfo(HttpServletRequest request,
                                                     HttpServletResponse response) {
        return accountService.getCurrentAccountInfo(request);
    }
}
