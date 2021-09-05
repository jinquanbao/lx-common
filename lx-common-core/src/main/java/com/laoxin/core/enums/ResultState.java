package com.laoxin.core.enums;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 返回结果接口
 * @author jinquanbao
 */
public interface ResultState {

    String getCode();

    String getMessage();

    int getHttpCode();

    static <T extends ResultState> T getEnum(String code, Class<T> clazz){

        return Stream.of(clazz.getEnumConstants())
                .filter(x-> Objects.equals(code,x.getCode()))
                .findAny().orElseGet(()->{
                    return null;
                });
    }
}
