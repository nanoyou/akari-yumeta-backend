package com.github.nanoyou.akariyumetabackend.interceptor.custom;

import org.springframework.web.servlet.HandlerInterceptor;

public class PostInterceptor extends CustomInterceptor{
    private PostInterceptor(HandlerInterceptor interceptor, Filter filter) {
        super(interceptor, filter);
    }

    public PostInterceptor(HandlerInterceptor interceptor) {
        super(interceptor, request -> request.getMethod().equals("POST"));
    }
}
