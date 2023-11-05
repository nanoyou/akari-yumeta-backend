package com.github.nanoyou.akariyumetabackend.common.interceptor;

import com.github.nanoyou.akariyumetabackend.common.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DonateInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //
        var user = (User) request.getSession().getAttribute(SessionAttr.LOGIN_USER_ID.attr);
        if (user == null || !user.getRole().equals(Role.SPONSOR)) {
            return false;
        }
        return true;
    }

}
