package com.zhujunji.common.utils;

import io.micrometer.core.lang.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Bean工具类<br>
 * 在非spring管理的类中获取spring注册的bean
 *
 * @Author cipher
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    /**
     * 安全的获取 Bean
     * @param name  Bean Name
     * @return Object
     */
    public static Object getBeanIfAvailable(String name) {
        if(applicationContext.containsBeanDefinition(name)){
            return applicationContext.getBean(name);
        }
        return null;
    }

    /**
     * 安全的获取 Bean
     * @param name  Bean Name
     * @param clazz Bean Type
     * @param <T>   T
     * @return T
     */
    public static <T> T getBeanIfAvailable(String name, Class<T> clazz) {
        if(applicationContext.containsBeanDefinition(name)){
            return applicationContext.getBean(name,clazz);
        }
        return null;
    }

    /**
     * 安全的获取 Bean
     * @param clazz Bean Type
     * @param <T>   T
     * @return T
     */
    public static <T> T getBeanIfAvailable(Class<T> clazz) {
        ObjectProvider<T> beanProvider = applicationContext.getBeanProvider(clazz);
        return beanProvider.getIfAvailable();
    }

}
