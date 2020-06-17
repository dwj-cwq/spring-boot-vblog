package com.dwj.vblogold.aop;

import com.alibaba.fastjson.JSON;
import com.dwj.vblogold.entity.LogEntity;
import com.dwj.vblogold.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.dwj.vblogold.aop.ControllerLog)")
    public void controllerPointcut() {

    }

    @Around(value = "controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        ControllerLog syslog = method.getAnnotation(ControllerLog.class);
        String msg = syslog.value();
        log.debug(msg, "hhw");
        // 记录url、ip等http参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String httpMethod = request.getMethod();
        String ip = request.getRemoteAddr();
        // 记录输出参数
        Map<String, String[]> params = request.getParameterMap();
        log.debug("==>Request: [ip:{}, url:{}, httpMethod:{}, Parameters:{}]", ip, url, httpMethod, JSON.toJSONString(params));
        // 计算耗费时间
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long takingTime = endTime - startTime;

        // 记录输出参数
        log.debug("<==Response: {}", JSON.toJSONString(result));

        // 记录耗费时间
        log.debug("{} in {} ms", url, takingTime);

        // 输入输出参数的最大长度为2048，超过最大长度，截取前面2048个字符
        String paramsDB = JSON.toJSONString(params).length() >= 2048 ? JSON.toJSONString(params).substring(0, 2048) : JSON.toJSONString(params);
        LogEntity logEntity = new LogEntity();
        logEntity.setIp(ip);
        logEntity.setMethod(httpMethod);
        logEntity.setParams(paramsDB);
        logEntity.setSpendTime((int)takingTime);
        logEntity.setOperation(msg);
        logEntity.setCreateTime(new Date());

        logService.saveLog(logEntity);

        return result;
    }

}
