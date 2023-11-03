package com.github.nanoyou.akariyumetabackend.common;

import jakarta.annotation.Nonnull;

public class NotImplementedException extends RuntimeException {

    private final String developer;

    public NotImplementedException(@Nonnull String developer) {
        this.developer = developer;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + "“" + developer + "”" + "未实现这里。";
    }
}
