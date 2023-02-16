package com.autocrypt.mon.security.provider;

import com.autocrypt.mon.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Authentication 화면에서 입력한 로그인정보
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        Account account = new Account();

        return new UsernamePasswordAuthenticationToken(account.getAccountId(), account.getPassword(), account.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

}
