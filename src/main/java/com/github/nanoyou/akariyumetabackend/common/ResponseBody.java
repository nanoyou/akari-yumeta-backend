package com.github.nanoyou.akariyumetabackend.common;

import com.github.nanoyou.akariyumetabackend.enumeration.ResponseCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
// 统一返回格式
public class ResponseBody {
    private ResponseCode code;
    private String msg;
    private Object data;

    public static ResponseBody success(Object data) {
        return ResponseBody.builder().code(ResponseCode.SUCCESS).msg("SUCCESS").data(data).build();
    }

    public static ResponseBody paramErr(Object data) {
        return ResponseBody.builder().code(ResponseCode.PARAM_ERR).msg("PARAM_ERR").data(data).build();
    }

    public static ResponseBody loginRequire(Object data) {
        return ResponseBody.builder().code(ResponseCode.LOGIN_REQUIRE).msg("LOGIN_REQUIRE").data(data).build();
    }

    public static ResponseBody unAutoAuthorized(Object data) {
        return ResponseBody.builder().code(ResponseCode.UNAUTHORIZED).msg("UN_AUTOAUTHORIZE").data(data).build();
    }

}
