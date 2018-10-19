/*
 *  Copyright (c) 2014-2018 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

