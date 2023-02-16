package com.autocrypt.mon.common;

import com.autocrypt.mon.account.AccountService;
import com.autocrypt.mon.account.dto.AccountDto;
import com.autocrypt.mon.account.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class CommonController implements ErrorController {

    @Autowired
    private AccountService accountService;

    // Return index.html
    @RequestMapping({"/login.html", "/"})
    public String loginPage() {
        return "index.html";
    }

    // If the login is successful, the user ID, last access time, and log check time are returned
    @ResponseBody
    @RequestMapping({"/loginSuccess"})
    public AccountDto.AccountLoginSuccessResponse loginSuccess(@RequestParam String loginId) {
        AccountDto.AccountLoginSuccessResponse accountLoginSuccessResponse = accountService.getAccountLoginSuccessResponse(loginId);
        return accountLoginSuccessResponse;
    }

    // When logout is successful, OK is returned
    @RequestMapping({"/logoutSuccess"})
    public ResponseEntity<?> logoutSuccess() {
        return ResponseEntity.ok("{\"result\" : \"ok\"}");
    }

    // Return OK if the session is valid
    @ResponseBody
    @RequestMapping("/session-check")
    public ResponseEntity<?> sessionCheck(HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        return ResponseEntity.ok("{\"result\" : \"ok\"}");
    }

    // Return index.html on error
    @RequestMapping("/error")
    public String handleError() {
        return "index.html";
    }

    // Return BAD_REQUEST in case of login error
    @RequestMapping("/error/login")
    public ResponseEntity<String> returnLoginErrRes() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or Password");
    }

}
