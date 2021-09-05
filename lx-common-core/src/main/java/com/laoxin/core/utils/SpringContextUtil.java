package com.laoxin.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
        log.info("applicationContext 配置成功 ");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {

        Object bean = applicationContext.getBean(name);
        if (bean == null) {
        	return null;
        }
        
        return (T)bean;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> beanClazz) throws BeansException {

        Object bean = applicationContext.getBean(beanClazz);
        if (bean == null) {
        	return null;
        }
        
        return (T)bean;
    }
}