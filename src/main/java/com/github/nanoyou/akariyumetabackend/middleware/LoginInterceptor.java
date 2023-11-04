package com.github.nanoyou.akariyumetabackend.middleware;

import com.github.nanoyou.akariyumetabackend.common.util.ResponseUtil;
import com.github.nanoyou.akariyumetabackend.common.enumeration.RequestAttr;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final ResponseUtil responseUtil;
    private final UserService userService;
    @Autowired
    public LoginInterceptor(ResponseUtil responseUtil, UserService userService) {
        this.responseUtil = responseUtil;
        this.userService = userService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var userID = request.getSession().getAttribute(SessionAttr.LOGIN_USER_ID.attr);
        if (userID == null) {
            responseUtil.sendJson(response, Result.builder()
                    .ok(false)
                    .code(ResponseCode.LOGIN_REQUIRE.value)
                    .message("需要登录")
                    .build()
            );
            return false;
        }
        var user = userService.getUser((String) userID);
        if (user.isEmpty()) {
            responseUtil.sendJson(response, Result.builder()
                    .ok(false)
                    .code(ResponseCode.LOGIN_REQUIRE.value)
                    .message("需要登录")
                    .build()
            );
            return false;
        }
        request.setAttribute(RequestAttr.LOGIN_USER.attr, user.get());

        return true;
    }
}
