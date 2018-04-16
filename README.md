# KumuluzEE Ethereum
[![Build Status](https://img.shields.io/travis/kumuluz/kumuluzee-ethereum/master.svg?style=flat)](https://travis-ci.org/kumuluz/kumuluzee-ethereum)

KumuluzEE Ethereum project for seamless development of Blockchain applications, smart contracts, distributed ledger and integration with Ethereum platform.
Pre-release version enables you to easily interact with Ethereum network using web3j. Web3j documentation can be found [here](https://docs.web3j.io/).

## Usage

You can enable KumuluzEE Ethereum support by adding the following dependency to pom.xml:
```xml
<dependency>
    <groupId>com.kumuluz.ee.ethereum</groupId>
    <artifactId>kumuluzee-ethereum-web3j</artifactId>
    <version>${kumuluzee-ethereum.version}</version>
</dependency>
```

## Connecting to network
In order to connect to the ethereum network you need to use a client. You can choose to run one yourself such as [geth](https://github.com/ethereum/go-ethereum/wiki/geth) or use client in the cloud such as [infura](https://infura.io/signup).
For testing purposes we recommend you connect to testnet (rovan, kovan or rinekby). In this guide we will use rinkeby. To get free test ether use [rinkeby faucet](https://faucet.rinkeby.io/)

## Creating a wallet

In order to make transactions you need to have a wallet. You can use your existing wallet or get a new one. You can also create a wallet using ethereum client.

Most popular options include:
* [MyEtherWallet (Online)](https://www.myetherwallet.com)
* [MetaMask](https://metamask.io)
* [Mist (Desktop)](https://github.com/ethereum/mist/releases)
* [Parity (Desktop)](https://ethcore.io/parity.html)

If you want to perform operations using your account you will need to provide wallet data in config.yaml.

### Configuration
To configure web3j instance create configuration in resources/config.yaml.
Here you can put path to your wallet and address of the client used to connect to the ethereum network.
```yaml
kumuluzee:
  ethereum:
    wallet:
      path: "/path/to/wallet.json"
      password: "kumuluzee"
    client:
      address: "http://localhost:8545"
```
### Contracts
If you wish do deploy or interact with Solidity smart contracts you should place them in resources folder.

## Usage
You can obtain [web3j instance](https://docs.web3j.io/quickstart.html) by using Web3jUtils.
```
private Web3jUtils web3jUtils = Web3jUtils.getInstance();
```
You can also inject web3j instance using CDI.
```
    @Inject
    @Web3jUtil
    private Web3j web3j;
```
## License

MIT
