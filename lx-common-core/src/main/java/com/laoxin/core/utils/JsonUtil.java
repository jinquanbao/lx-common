package com.laoxin.core.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laoxin.core.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = null;

    @Autowired
    public JsonUtil(ObjectMapper objectMapper){
        JsonUtil.objectMapper = objectMapper;
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        if(StringUtils.isEmpty(json)){
            return null;
        }
        try {
           return objectMapper.readValue(json,classOfT);
        } catch (JsonParseException e) {
            log.error("json parse error {}",e.getMessage());
            throw new ServerException("json解析异常",e);
        } catch (JsonMappingException e) {
            log.error("json parse error {}",e.getMessage());
            throw new ServerException("json解析异常",e);
        } catch (IOException e) {
            log.error("json parse error {}",e.getMessage());
            throw new ServerException("json解析异常",e);
        }
    }

    public static <T> List<T> fromJsonArray(String json, Class<T> classOfT){
        if(StringUtils.isEmpty(json)){
            return null;
        }
        try {
            JavaType jt = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, classOfT);
            return objectMapper.readValue(json,jt);
        } catch (JsonParseException e) {
            log.error("json parse error {}",e.getMessage());
            throw new ServerException("json解析异常",e);
        } catch (JsonMappingException e) {
            log.error("json parse error {}",e.getMessage());
            throw new ServerException("json解析异常",e);
        } catch (IOException e) {
            log.error("json parse error {}",e.getMessage());
            throw new ServerException("json解析异常",e);
        }
    }

    public static String toJson(Object obj){
        if(null == obj){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json processing error {}",e.getMessage());
            throw new ServerException("json读取异常",e);
        }
    }
}
