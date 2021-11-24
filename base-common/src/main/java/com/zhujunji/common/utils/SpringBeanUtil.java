package com.zhujunji.common.utils;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;

/**
 * Bean工具类<br>
 * 在非spring管理的类中获取spring注册的bean
 *
 * @Author cipher
 */
public class SpringBeanUtil {

    /**
     * 安全的获取 Bean
     * @param name  Bean Name
     * @return Object
     */
    public static Object getBeanIfAvailable(ApplicationContext applicationContext, String name) {
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
    public static <T> T getBeanIfAvailable(ApplicationContext applicationContext, String name, Class<T> clazz) {
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
    public static <T> T getBeanIfAvailable(ApplicationContext applicationContext, Class<T> clazz) {
        ObjectProvider<T> beanProvider = applicationContext.getBeanProvider(clazz);
        return beanProvider.getIfAvailable();
    }

}
