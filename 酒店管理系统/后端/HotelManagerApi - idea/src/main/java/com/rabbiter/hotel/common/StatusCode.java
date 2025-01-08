package com.rabbiter.hotel.common;

public enum StatusCode {

    COMMON_SUCCESS(ConstantCode.SUCCESS, "成功。"),
    COMMON_FAIL(ConstantCode.FAIL, "失败")
    ;


    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    StatusCode() {
    }

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
