package com.github.nanoyou.akariyumetabackend.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DonateConfig implements WebMvcConfigurer {

    @Autowired
    private DonateInterceptor donateInterceptor;



}
