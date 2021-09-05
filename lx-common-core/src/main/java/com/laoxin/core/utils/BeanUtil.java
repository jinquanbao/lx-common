package com.laoxin.core.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanMap;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.*;


public class BeanUtil {

    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private static String[] getNullPropertyNamesAndIgnore (Object source,String... ignoreProperties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : Collections.emptyList());
        emptyNames.addAll(ignoreList);
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static void copyProperties(Object src, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(src, target, getNullPropertyNamesAndIgnore(src,ignoreProperties));
    }

    public static <T> T copyProperties(Object src, Class<T> targetClass){
        T target = null;
        if(src != null ){
            target = BeanUtils.instantiateClass(targetClass);
            copyProperties(src,target);
        }
        return target;
    }

    /**
     * 复制属性，手动指定忽略属性
     */
    public static <T> T copyProperties(Object src, Class<T> targetClass, String... ignoreProperties){
        T target = null;
        if(src != null ){
            target = BeanUtils.instantiateClass(targetClass);
            copyProperties(src, target,ignoreProperties);
        }
        return target;
    }

    public static <T> Map<String, Object> bean2Map(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /***
     * 属性复制，默认忽略为空属性
     */
    public static <T> List<T> copyProperties(List<?> srcList,Class<T> targetClass) {
        List<T> targetList = new ArrayList<T>();
        if(srcList != null){
            for (Iterator<?> it = srcList.iterator(); it.hasNext();) {
                Object source = it.next();
                try {
                    if(source != null){
                        T target = targetClass.newInstance();
                        copyProperties(source, target);
                        targetList.add(target);
                    }
                } catch (Exception ex) {

                }
            }
        }
        return targetList;
    }

    /***
     * 属性复制，手动忽略复制属性
     */
    public static <T> List<T> copyProperties(List<?> srcList,Class<T> targetClass, String[] ignoreProperties) {
        List<T> targetList = new ArrayList<T>();
        if(srcList != null){
            for (Iterator<?> it = srcList.iterator(); it.hasNext();) {
                Object source = it.next();
                try {
                    if(source != null){
                        T target = targetClass.newInstance();
                        copyProperties(source, target,ignoreProperties);
                        targetList.add(target);
                    }
                } catch (Exception ex) {

                }
            }
        }
        return targetList;
    }
}
