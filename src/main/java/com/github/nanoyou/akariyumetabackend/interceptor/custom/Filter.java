package com.github.nanoyou.akariyumetabackend.interceptor.custom;

import jakarta.servlet.http.HttpServletRequest;

public interface Filter {
    boolean check(HttpServletRequest request);
}
