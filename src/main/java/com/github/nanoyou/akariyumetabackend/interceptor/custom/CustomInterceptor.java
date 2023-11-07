package com.github.nanoyou.akariyumetabackend.interceptor.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CustomInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor interceptor;
    private final Filter filter;

    public CustomInterceptor(HandlerInterceptor interceptor, Filter filter) {
        this.interceptor = interceptor;
        this.filter = filter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (filter.check(request)) {
            return interceptor.preHandle(request, response, handler);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (filter.check(request)) {
            interceptor.postHandle(request, response, handler, modelAndView);
        }
    }
}
