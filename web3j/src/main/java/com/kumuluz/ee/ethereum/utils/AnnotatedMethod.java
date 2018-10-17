package com.kumuluz.ee.ethereum.utils;

import javax.enterprise.inject.spi.Bean;
import java.lang.reflect.Method;

/**
 * @author Domen Gašperlin
 * @since 1.0.0
 */

public class AnnotatedMethod<T> {
    private Bean bean;
    private Method method;
    private T annotation;

    private Class pojo;

    public AnnotatedMethod(Bean bean, Method method, T annotation) {
        this.bean = bean;
        this.method = method;
        this.annotation = annotation;
    }

    public AnnotatedMethod(Class clazz, Method method, T annotation) {
        this.method = method;
        this.annotation = annotation;
        this.pojo = clazz;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public T getAnnotation() {
        return annotation;
    }

    public void setAnnotation(T annotation) {
        this.annotation = annotation;
    }

    public Class getClazz() {
        return pojo;
    }

    public void setClazz(Class pojo) {
        this.pojo = pojo;
    }
}

