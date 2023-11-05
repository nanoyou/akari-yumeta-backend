package com.github.nanoyou.akariyumetabackend.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {
    private final ObjectMapper mapper;
    @Autowired
    public ResponseUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    public void sendJson(HttpServletResponse res, Object data) {
        res.setHeader("Content-Type", "application/json;charset=utf-8");
        try {
            res.getWriter().write(mapper.writeValueAsString(data));
        } catch (Exception ignore) {}
        res.setStatus(200);
    }
}
