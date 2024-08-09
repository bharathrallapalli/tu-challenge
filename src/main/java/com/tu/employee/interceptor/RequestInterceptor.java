package com.tu.employee.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Request received for path: {}, method: {}", request.getRequestURI(), request.getMethod());
        if(request.getHeader("Request-Id") == null) {
            throw new IllegalArgumentException("Request-Id header is missing");
        }
        MDC.put("request.id", request.getHeader("Request-Id"));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
