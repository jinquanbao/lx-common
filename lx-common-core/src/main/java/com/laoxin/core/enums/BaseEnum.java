package com.laoxin.core.enums;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 基础枚举
 * @author jinquanbao
 */
public interface BaseEnum {

    //默认type，如果复写了getDefault方法，以复写为准
    int DEFAULT_TYPE = -1;

    //默认code，如果复写了getDefault方法，以复写为准
    String DEFAULT_CODE = "";

    //默认desc，如果复写了getDefault方法，以复写为准
    String DEFAULT_DESC = "未知";

    int getValue();

    String getCode();

    String getDesc();

    default <T extends BaseEnum> T getDefault() {
        return null;
    }


    /**
     * 根据type 获取相应的枚举，如果未找到，实现的枚举复写了getDefault()方法，返回getDefault()结果，否则返回空
     * @return
     */
    static <T extends BaseEnum> T getEnum(int value, Class<T> clazz){

        return Stream.of(clazz.getEnumConstants())
                .filter(x-> Objects.equals(value,x.getValue()))
                .findAny().orElseGet(()->{
                    Optional<T> any = Stream.of(clazz.getEnumConstants()).findAny();
                    T def = null;
                    if(any.isPresent()){
                        def = any.get().getDefault();
                    }
                    return def;
                });
    }

    /**
     * 根据code 获取相应的枚举，如果未找到，实现的枚举复写了getDefault()方法，返回getDefault()结果，否则返回空
     * @return
     */
    static <T extends BaseEnum> T getEnum(String code, Class<T> clazz){

        return Stream.of(clazz.getEnumConstants())
                .filter(x-> Objects.equals(code,x.getCode()))
                .findAny().orElseGet(()->{
                    Optional<T> any = Stream.of(clazz.getEnumConstants()).findAny();
                    T def = null;
                    if(any.isPresent()){
                        def = any.get().getDefault();
                    }
                    return def;
                });
    }

    static <T extends BaseEnum> String getCode(int value, Class<T> clazz){
        BaseEnum baseEnum = getEnum(value, clazz);
        if(null != baseEnum){
            return baseEnum.getCode();
        }
        return DEFAULT_CODE;
    }

    static <T extends BaseEnum> int getValue(String code, Class<T> clazz){
        BaseEnum baseEnum = getEnum(code, clazz);
        if(null != baseEnum){
            return baseEnum.getValue();
        }
        return DEFAULT_TYPE;
    }

    static <T extends BaseEnum> String getDesc(int value, Class<T> clazz){
        BaseEnum baseEnum = getEnum(value, clazz);
        if(null != baseEnum){
            return baseEnum.getDesc();
        }
        return DEFAULT_DESC;
    }

    static <T extends BaseEnum> String getDesc(String code, Class<T> clazz){
        BaseEnum baseEnum = getEnum(code, clazz);
        if(null != baseEnum){
            return baseEnum.getDesc();
        }
        return DEFAULT_DESC;
    }




}
