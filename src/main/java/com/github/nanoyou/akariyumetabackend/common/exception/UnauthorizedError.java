package com.github.nanoyou.akariyumetabackend.common.exception;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;

public class UnauthorizedError extends BaseError {
    protected UnauthorizedError(ResponseCode code, String message) {
        super(code, message);
    }
}
