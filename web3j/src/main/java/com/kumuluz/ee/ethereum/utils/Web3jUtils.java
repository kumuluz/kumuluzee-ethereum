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

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Web3jUtil class used for getting instance of Web3jUtil, web3j and credentials.
 *
 * @author Domen Gašperlin
 * @since 1.0.0
 */

public class Web3jUtils {

    private static Web3jUtils web3jUtils;
    private Web3j web3j;
    private static Credentials credentials;
    private static final Logger log = Logger.getLogger(Web3jUtils.class.getName());

    protected Web3jUtils() {
        ConfigurationUtil configurationUtil = ConfigurationUtil.getInstance();
        String clientAddress = configurationUtil.get("kumuluzee.ethereum.client.address").orElse("http://localhost:8545");
        this.web3j = Web3j.build(new HttpService(clientAddress));

        try {
            credentials = loadWallet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initialize() {
        web3jUtils = new Web3jUtils();
    }

    public static Web3jUtils getInstance() {
        if (web3jUtils == null) {
            throw new IllegalStateException("The Web3jUtil was not yet initialized.");
        }
        return web3jUtils;
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public static Credentials getCredentials() {
        if (credentials == null) {
            throw new IllegalStateException("The Credentials were not yet provided.");
        }
        return credentials;
    }

    public Credentials loadWallet() throws Exception {
        Optional walletPath = ConfigurationUtil.getInstance().get("kumuluzee.ethereum.wallet.path");
        Optional walletPassword = ConfigurationUtil.getInstance().get("kumuluzee.ethereum.wallet.password");
        if (walletPassword.isPresent() && walletPassword.isPresent()) {
            return WalletUtils.loadCredentials(String.valueOf(walletPassword.get()), String.valueOf(walletPath.get()));
        } else {
            log.info("Wallet was not loaded, no path or credentials supplied.");
            return null;
        }
    }
}