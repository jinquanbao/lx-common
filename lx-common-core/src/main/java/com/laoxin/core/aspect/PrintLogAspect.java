package com.laoxin.core.aspect;

import com.laoxin.core.annotation.PrintLogAnnotation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 统一日志切面处理，需要在接口上加 @PrintLogAnnotation 注解
 * @author jinquanbao
 */
@Component
@Aspect
@Slf4j
public class PrintLogAspect {

    @Pointcut("@annotation(com.laoxin.core.annotation.PrintLogAnnotation)")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object logAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!log.isInfoEnabled()) {
            return joinPoint.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method m = methodSignature.getMethod();
        PrintLogAnnotation printLogAnnotation = m.getAnnotation(PrintLogAnnotation.class);
        if(printLogAnnotation == null){
            return joinPoint.proceed();
        }

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        Object[] args = joinPoint.getArgs();
        Map<String,Object> logMap = new LinkedHashMap<>();
        logMap.put("请求uri",uri);
        logMap.put("请求method",method);
        List<Object> params = new ArrayList<>();
        if( args!= null){
            try{
                for(Object arg : args){
                    if(!(arg instanceof HttpServletRequest) && !(arg instanceof MultipartFile)) {
                        params.add(arg);
                    }
                }
                logMap.put("请求参数",params);
                log.info("[logAspect]REQUEST--{}",objectMapper.writeValueAsString(logMap));
                logMap.remove("请求参数");
            }catch (Exception e){
                log.warn("[logAspect]uri为{}的请求无法转换请求参数。msg={}",uri,e.getMessage());
            }
        }

        long start = System.currentTimeMillis();
        try {
            Object rspObj = joinPoint.proceed();
            logMap.put("请求耗时ms",System.currentTimeMillis()-start);
            //打印返回数据，有些响应接口返回数据太多不想打印响应数据 可以置为false,默认true
            if(printLogAnnotation.printResponseData()){
                logMap.put("返回数据",rspObj);
            }
            String responseStr = "";
            try{
                responseStr = objectMapper.writeValueAsString(logMap);
                log.info("[logAspect]SUCCESS--{}", responseStr);
            }catch (Exception e){
                log.warn("[logAspect]uri为{}的请求无法转换返回数据。msg={}",uri,e.getMessage());
            }
            return rspObj;
        }
        catch (Throwable e) {
            throw e;
        }
    }

}
