package com.github.nanoyou.akariyumetabackend.handler;

import com.github.nanoyou.akariyumetabackend.common.exception.BaseError;
import com.github.nanoyou.akariyumetabackend.common.exception.UnauthorizedError;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorHandler.class);

    private void logReason(Exception err) {
        logger.error("全局错误处理器捕获到了一个" + err.getClass() + "异常：\n" + err.getMessage());
    }

    @ExceptionHandler(value = BaseError.class)
    @ResponseBody
    public Result unauthorizedErrorHandler(BaseError err) {
        logReason(err);
        return Result.failed(err.getMessage(), err.getCode());
    }

}
