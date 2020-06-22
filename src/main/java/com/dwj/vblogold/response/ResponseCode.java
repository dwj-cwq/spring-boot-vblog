package com.dwj.vblogold.response;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public enum ResponseCode {
    OK(0, "成功"),
    BAD_REQUEST(400, "错误请求"),
    NOT_LOGIN(1004, "未登录"),
    USER_NO_EXISTS(1005, "用户名不存在"),
    PASSWORD_ERROR(1006, "密码错误"),
    NOT_GET_CAPTCHA(1007, "请先获取校验码"),
    CAPTCHA_VERIFY_FAILED(1008, "校验码未校验通过"),
    UNKNOWN_REASON(2000, "未知错误，请联系管理员");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public ResponseCode setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseCode setMessage(String message) {
        this.message = message;
        return this;
    }
}
