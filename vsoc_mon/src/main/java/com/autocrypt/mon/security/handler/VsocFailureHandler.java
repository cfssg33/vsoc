package com.autocrypt.mon.security.handler;

import com.autocrypt.mon.config.LoginResponseType;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class VsocFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        LoginResponseType getLoginErrorType = GetLoginErrorType(exception);

        response.sendRedirect("/error/login");
    }

    private LoginResponseType GetLoginErrorType (AuthenticationException e) {

        LoginResponseType getLoginErrorType;
        if (e instanceof LockedException) {
            getLoginErrorType = LoginResponseType.LOCK_PASSWORD_EXP;
        } else if (e instanceof CredentialsExpiredException) {
            getLoginErrorType = LoginResponseType.BAD_CREDENTIAL;
        } else if (e instanceof BadCredentialsException) {
            getLoginErrorType = LoginResponseType.NOT_FOUNT_USER;
        } else {
            getLoginErrorType = LoginResponseType.UNKNOWN;
        }

        return getLoginErrorType;
    }

}
