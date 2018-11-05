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
import com.kumuluz.ee.ethereum.annotations.EventListen;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tx.Contract;
import rx.Observable;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Logger;

/**
 * @author Domen Gašperlin
 * @since 1.0.0
 */

public class ListenerInitializer implements EventListenerInitializer {

    private static final Logger log = Logger.getLogger(ListenerInitializer.class.getName());

    Credentials credentials = Web3jUtils.getCredentials();


    private Web3j web3j = Web3jUtils.getInstance().getWeb3j();


    public void after(@Observes AfterDeploymentValidation adv, BeanManager bm) {


        for (AnnotatedMethod inst : instanceList) {
            log.fine("Found method " + inst.getMethod().getName() + " in class " + inst.getMethod().getDeclaringClass());
        }

        if (instanceList.size() > 0) {

            for (AnnotatedMethod<EventListen> inst : instanceList) {

                EventListen annotation = inst.getAnnotation();
                Method method = inst.getMethod();

                // Annotation parameters
                String eventName = annotation.eventName();
                Class scClass = annotation.smartContractName();
                String scAddress = annotation.smartContractAddress();

                // Instance of the annotated method
                Object instance = bm.getReference(inst.getBean(), method.getDeclaringClass(), bm
                        .createCreationalContext(inst.getBean()));





                try {

                    Method loadMethod = scClass.getMethod("load",
                            String.class,Web3j.class,Credentials.class,Contract.GAS_PRICE.getClass(),Contract.GAS_LIMIT.getClass());

                    Contract smartContractInstance = (Contract)loadMethod.invoke(scClass,
                            scAddress,web3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);

                    Class<?> params2[] = {DefaultBlockParameter.class,DefaultBlockParameter.class};
                    Object arguments2[] = {DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST};

                    Object obj2 = invokeMethod(smartContractInstance,eventName+"EventObservable",params2,arguments2);

                    ((Observable<?>)obj2).subscribe(x -> {
                        try {
                            log.info("Calling Method " + method.getName()+"() due to "+eventName + " event.");

                            method.invoke(instance,x);
                        } catch (Exception e) {
                            log.info(e.getMessage());
                        }

                    });

                } catch (Exception e) {
                    log.severe("Method invocation for " + eventName + " event has failed. Please provide valid event name!");
                }
            }
        }
    }


    public static Object invokeMethod(Object obj, String methodName, Class<?> parameters[], Object arguments[]) throws Exception {
        Class cls = obj.getClass();
        Method m1 = cls.getDeclaredMethod(methodName,parameters);
        Object obj2 = m1.invoke(obj, arguments);
        return obj2;
    }


}
