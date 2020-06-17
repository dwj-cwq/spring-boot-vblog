package com.dwj.vblogold.aop;

import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseBody
    public JsonResponse handleUnknownAccountException(HttpServletRequest request, UnknownAccountException e) {
        log.warn("Unknown Account Exception", e);

        return JsonResponse.response(ResponseCode.USER_NO_EXISTS);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    public JsonResponse handleUnknownAccountException(HttpServletRequest request, IncorrectCredentialsException e) {
        log.warn("Incorrect Credential Exception", e);
        return JsonResponse.response(ResponseCode.PASSWORD_ERROR);
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResponse handle(HttpServletRequest request, Exception e) {
        log.error("Other Exception", e);
        return JsonResponse.response(ResponseCode.UNKNOWN_REASON);
    }

}
