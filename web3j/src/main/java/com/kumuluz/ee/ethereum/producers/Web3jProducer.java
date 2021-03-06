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

package com.kumuluz.ee.ethereum.producers;

import com.kumuluz.ee.ethereum.annotations.Web3jUtil;
import com.kumuluz.ee.ethereum.utils.Web3jUtils;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Enables CDI injection
 *
 * @author Domen Gašperlin
 * @since 1.0.0
 */
@ApplicationScoped
public class Web3jProducer {
    @Produces
    @Web3jUtil
    public Web3j produceWeb3jUtil(InjectionPoint injectionPoint) {
        return Web3jUtils.getInstance().getWeb3j();
    }

    @Produces
    public Credentials produceCredentialsUtil(InjectionPoint injectionPoint) {
        return Web3jUtils.getCredentials();
    }

}
