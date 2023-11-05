package com.github.nanoyou.akariyumetabackend.common.config;

import com.github.nanoyou.akariyumetabackend.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final AdminInterceptor adminInterceptor;
    private final ChildInterceptor childInterceptor;
    private final VolunteerInterceptor volunteerInterceptor;
    private final SponsorInterceptor sponsorInterceptor;
    @Autowired
    public InterceptorConfig(
            LoginInterceptor loginInterceptor,
            AdminInterceptor adminInterceptor,
            ChildInterceptor childInterceptor,
            VolunteerInterceptor volunteerInterceptor,
            SponsorInterceptor sponsorInterceptor
    ) {
        this.loginInterceptor = loginInterceptor;
        this.adminInterceptor = adminInterceptor;
        this.childInterceptor = childInterceptor;
        this.volunteerInterceptor = volunteerInterceptor;
        this.sponsorInterceptor = sponsorInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");

        // 在下面添加需要登录的路径
        registry.addInterceptor(loginInterceptor)
//                添加示例
//                .addPathPatterns("/admin/**");
                .addPathPatterns("/my/**");

        // 在下面添加需要 管理员 登录的路径，需要添加至上方 loginInterceptor 内
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin");
//                添加示例
//                .addPathPatterns("/admin/**");

        // 在下面添加需要 儿童 登录的路径，需要添加至上方 loginInterceptor 内
        registry.addInterceptor(childInterceptor)
                .addPathPatterns("/child");
//                添加示例
//                .addPathPatterns("/child/**")

        // 在下面添加需要 志愿者 登录的路径，需要添加至上方 loginInterceptor 内
        registry.addInterceptor(volunteerInterceptor)
                .addPathPatterns("/volunteer");
//                添加示例
//                .addPathPatterns("/volunteer/**")

        // 在下面添加需要 捐助者 登录的路径，需要添加至上方 loginInterceptor 内
        registry.addInterceptor(sponsorInterceptor)
                .addPathPatterns("/sponsor")
                .addPathPatterns("/donatem/goods")
                .addPathPatterns("/donate/money")
                .addPathPatterns("/donate/**/info")
                .addPathPatterns("/donate/goods/**");
//                添加示例
//                .addPathPatterns("/sponsor/**")

    }
}
