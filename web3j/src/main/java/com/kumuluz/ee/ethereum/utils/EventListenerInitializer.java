package com.kumuluz.ee.ethereum.utils;

import com.kumuluz.ee.common.dependencies.EeExtensionDef;
import com.kumuluz.ee.ethereum.annotations.EventListen;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Domen Gašperlin
 * @since 1.0.0
 */

public interface EventListenerInitializer extends Extension {

    List<AnnotatedMethod<EventListen>> instanceList = new ArrayList<>();

    default <X> void processStreamListeners(@Observes ProcessBean<X> pat) {

        for (Method method : pat.getBean().getBeanClass().getMethods()) {
            if (method.getAnnotation(EventListen.class) != null) {

                EventListen annotation = method.getAnnotation(EventListen.class);

                instanceList.add(new AnnotatedMethod<>(pat.getBean(), method, annotation));
            }
        }
    }

    void after(@Observes AfterDeploymentValidation adv, BeanManager bm);

}