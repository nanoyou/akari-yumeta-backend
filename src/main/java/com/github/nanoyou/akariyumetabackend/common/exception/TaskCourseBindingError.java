package com.github.nanoyou.akariyumetabackend.common.exception;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import jakarta.annotation.Nonnull;

public class TaskCourseBindingError extends BaseError{
    public TaskCourseBindingError(@Nonnull ResponseCode code, @Nonnull String message) {
        super(code, message);
    }
}
