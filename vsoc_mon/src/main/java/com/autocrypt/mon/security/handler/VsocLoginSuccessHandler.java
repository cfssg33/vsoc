package com.autocrypt.mon.security.handler;

import com.autocrypt.mon.account.AccountRepository;
import com.autocrypt.mon.util.SessionUtil;
import com.autocrypt.mon.util.dto.SessionInfoDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class VsocLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        HttpSession session = request.getSession();
        String loginId = authentication.getName();

        session.setMaxInactiveInterval(300);

        session.setAttribute("loginId", loginId);
        session.setAttribute("account", accountRepository.findByAccountId(loginId));

        StringBuilder redirectionUrl = new StringBuilder("/loginSuccess?");
        redirectionUrl.append("loginId=").append(loginId);

        response.sendRedirect(redirectionUrl.toString());
    }

}
