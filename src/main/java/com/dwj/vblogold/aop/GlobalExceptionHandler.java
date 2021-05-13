package com.dwj.vblogold.aop;

import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return wrapper(ResponseCode.USER_NO_EXISTS, request.getMethod(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    public JsonResponse handleUnknownAccountException(HttpServletRequest request, IncorrectCredentialsException e) {
        log.warn("Incorrect Credential Exception", e);
        return wrapper(ResponseCode.PASSWORD_ERROR, request.getMethod(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResponse handle(HttpServletRequest request, Exception e) {
        log.error("Other Exception", e);
        return wrapper(ResponseCode.UNKNOWN_REASON, request.getMethod(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public JsonResponse handleMethodValid(HttpServletRequest request, Exception e) {
        log.error("Method valid Exception", e);
        // 捕获参数校验异常，并封装返回
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            FieldError fieldError = bindException.getBindingResult().getFieldError();
            String localizedMessage = fieldError != null ? fieldError.getDefaultMessage() : "";
            return wrapper(HttpStatus.METHOD_NOT_ALLOWED, request.getMethod(), request.getRequestURI(), localizedMessage);
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            FieldError fieldError = validException.getBindingResult().getFieldError();
            String localizedMessage = fieldError != null ? fieldError.getDefaultMessage() : "";
            return wrapper(HttpStatus.METHOD_NOT_ALLOWED, request.getMethod(), request.getRequestURI(), localizedMessage);
        }
        return wrapper(ResponseCode.UNKNOWN_REASON, request.getMethod(), request.getRequestURI(), e.getMessage());
    }

    private static JsonResponse wrapper(HttpStatus httpStatus, String method, String uri, String message) {
        log.warn("[ExceptionHandler] request method: {}, request uri: {}, warn message: {}", method, uri, message);
        return new JsonResponse(httpStatus.value(), message, httpStatus.getReasonPhrase());
    }

    private static JsonResponse wrapper(ResponseCode httpStatus, String method, String uri, String message) {
        log.warn("[ExceptionHandler] request method: {}, request uri: {}, warn message: {}", method, uri, message);
        return new JsonResponse(httpStatus.getCode(), message, httpStatus.getMessage());
    }

}
