package com.autocrypt.mon.filter;

import com.autocrypt.mon.wrapper.HttpRequestDataWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HttpLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                         throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpRequestDataWrapper wrapperRequest = new HttpRequestDataWrapper(httpServletRequest);
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(httpServletResponse);

        chain.doFilter(wrapperRequest, wrapperResponse);
    }

}
