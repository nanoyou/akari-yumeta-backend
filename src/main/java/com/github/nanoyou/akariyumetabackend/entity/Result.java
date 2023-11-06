package com.github.nanoyou.akariyumetabackend.entity;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    /**
     * 是否成功
     */
    private boolean ok;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

    public static Result failed(@Nonnull String msg, ResponseCode responseCode) {
        return Result.builder()
                .ok(false)
                .code(responseCode.value)
                .message(msg)
                .data(null)
                .build();
    }

    public static Result success(@Nonnull String msg, @Nonnull Object data) {
        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .message(msg)
                .data(data)
                .build();
    }

}
