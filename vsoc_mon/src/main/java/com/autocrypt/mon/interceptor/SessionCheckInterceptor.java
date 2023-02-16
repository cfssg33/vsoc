package com.autocrypt.mon.interceptor;

import com.autocrypt.mon.account.entity.Account;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;


@Component
public class SessionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String loginId = (String)session.getAttribute("loginId");
        Account accountInfo = (Account) session.getAttribute("account");

        if(Objects.isNull(loginId) && Objects.isNull(accountInfo)) {
            response.setStatus(304);
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
