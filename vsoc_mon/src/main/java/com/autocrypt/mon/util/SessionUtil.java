package com.autocrypt.mon.util;

import com.autocrypt.mon.util.dto.SessionInfoDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtil {

    public static SessionInfoDto getSessionInfo() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        return getSessionInfo(request);
    }

    public static SessionInfoDto getSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if(Objects.isNull(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }

        String loginId = (String)session.getAttribute("loginId");
        String role = (String)session.getAttribute("role");

        return new SessionInfoDto(session.getId(), remoteAddr, loginId, role);
    }

}
