package com.kumuluz.ee.ethereum.utils;

import com.kumuluz.ee.common.config.EeConfig;
import com.kumuluz.ee.common.wrapper.KumuluzServerWrapper;
import com.kumuluz.ee.ethereum.annotations.EventListen;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import rx.Observable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Domen Gašperlin
 * @since 1.0.0
 */

public class ListenerInitializer implements EventListenerInitializer {

    private static final Logger log = Logger.getLogger(ListenerInitializer.class.getName());

    public void after(@Observes AfterDeploymentValidation adv, BeanManager bm) {


        for (AnnotatedMethod inst : instanceList) {
            log.fine("Found consumer method " + inst.getMethod().getName() + " in class " + inst.getMethod().getDeclaringClass());
        }

        if (instanceList.size() > 0) {

            for (AnnotatedMethod<EventListen> inst : instanceList) {

                EventListen annotation = inst.getAnnotation();
                Method method = inst.getMethod();

                String eventName = annotation.eventName();
                Class scName = annotation.smartContractName();

                Object instance = bm.getReference(inst.getBean(), method.getDeclaringClass(), bm
                        .createCreationalContext(inst.getBean()));
                log.severe(eventName);



                try {

                    Object smartContractInstance = eventName.getClass().newInstance();

                    (runMethod(smartContractInstance,eventName+"EventObservable",
                            DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.EARLIEST.getClass(),
                            DefaultBlockParameterName.LATEST,DefaultBlockParameterName.LATEST.getClass()))
                            .observable().subscribe(x -> {
                                log.info(x.toString());
                                try {
                                    method.invoke(method.getParameters());
                                } catch (Exception e) {
                                    log.severe("Error method can't be called back!");
                                }
                            });

//                    ((Observable<?>)scName.getMethod(eventName+"EventObservable")
//                            .invoke(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST))
//                            .asObservable().subscribe(x -> {
//                                log.info(x.toString());
//                                try {
//                                    method.invoke(method.getParameters());
//                                } catch (Exception e) {
//                                    log.severe("Error method can't be called back!");
//                                }
//
//                            }
//                    );



                } catch (Exception e) {
                    log.severe("Method invocation for " + eventName + " has failed. Please provide valid event name!");
                }



            }
        }
    }


    private static RemoteCall runMethod(Object instance, String methodName, DefaultBlockParameterName argo, Class<?> parameterType,DefaultBlockParameterName argo2, Class<?> parameterType2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = instance.getClass().getMethod(methodName, parameterType,parameterType2);
        return (RemoteCall)method.invoke(instance, argo, argo2);
    }



}
