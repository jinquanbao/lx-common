package com.laoxin.core.aspect;

import com.laoxin.core.annotation.IgnoreResponseAdviceAnnotation;
import com.laoxin.core.vo.ResponseVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 响应体包装
 * @author jinquanbao
 */
@RestControllerAdvice("com.laoxin.")
public class ResponseAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter parameter, Class converterType) {

        Method method = parameter.getMethod();
        if (method == null){
            return false;
        }
        if(method.isAnnotationPresent(IgnoreResponseAdviceAnnotation.class)){
            return false;
        }
        Class returnType = method.getReturnType();
        if (returnType == ResponseVO.class || returnType ==  ModelAndView.class || returnType == ResponseEntity.class || "void".equals(returnType.getName())){
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        return ResponseVO.ok(body);
    }
}
