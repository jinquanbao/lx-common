package com.laoxin.core.utils;

import java.util.Collection;
import java.util.Map;

public class Util {
    public static boolean isEmpty(String str){
        return str == null || "".equals(str);
    }
    public static boolean isEmpty(Collection list){
        return list == null || list.isEmpty();
    }
    public static boolean isEmpty(Map map){
        return map == null || map.isEmpty();
    }
    public static boolean isEmpty(Object obj){
        return obj == null;
    }
}
