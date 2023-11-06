package com.github.nanoyou.akariyumetabackend.common.exception;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BaseError extends RuntimeException {
    protected ResponseCode code;
    protected String message;

    protected BaseError(@Nonnull ResponseCode code, @Nonnull String message) {
        this.message = message;
        this.code = code;
    }

    public static void raise(@Nonnull ResponseCode code, @Nonnull String message) {
        throw new UnauthorizedError(code, message);
    }
}
