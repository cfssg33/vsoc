package com.autocrypt.mon.interceptor;

import com.autocrypt.mon.log.LoggerService;
import com.autocrypt.mon.util.SessionUtil;
import com.autocrypt.mon.wrapper.HttpRequestDataWrapper;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class ApiLoggingInterceptor implements HandlerInterceptor {

    @Autowired
    private LoggerService loggerService;

    private Logger logger = LoggerFactory.getLogger(ApiLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        logger.info("Request [{}] [{}] [{}] [{}] {}", request.getMethod(),
                                                      request.getContentType(),
                                                      request.getRequestURI(),
                                                      SessionUtil.getSessionInfo(request).getRemoteAddr(),
                                                      getRequestParameter(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                                throws IOException {

        if (response instanceof ContentCachingResponseWrapper) {
            ContentCachingResponseWrapper wrapperResponse = (ContentCachingResponseWrapper)response;

            int status = response.getStatus();
            String url = request.getRequestURI();
            String httpMethod = request.getMethod();
            String responseStr = new String(wrapperResponse.getContentAsByteArray());
            String reqParam = getRequestParameter(request);
            Duration duration = getDuration(request);
            wrapperResponse.copyBodyToResponse();

            loggerService.InsertApiLog(url,
                                       status,
                                       SessionUtil.getSessionInfo(request).getRemoteAddr(),
                                       httpMethod,
                                       reqParam,
                                       responseStr,
                                       duration);
        }
    }

    public static String getRequestParameter(HttpServletRequest request) {
        String requestStr = "";

        if (HttpRequestDataWrapper.class.isInstance(request) ) {
            HttpRequestDataWrapper wrapperRequest = (HttpRequestDataWrapper) request;
            requestStr = wrapperRequest.getRequestData();
            if (null == requestStr) {
                requestStr = "Empty";
            }
        }

        return requestStr;
    }

    public static Duration getDuration(HttpServletRequest request) {
        if (HttpRequestDataWrapper.class.isInstance(request)) {
            HttpRequestDataWrapper wrapperRequest = (HttpRequestDataWrapper)request;
            LocalDateTime createdTime = wrapperRequest.getRequestCreated();

            if (null != createdTime) {
                return Duration.between(createdTime, LocalDateTime.now());
            }
        }

        return Duration.between(LocalDateTime.now(), LocalDateTime.now());
    }

}
