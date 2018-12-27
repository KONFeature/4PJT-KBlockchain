# 4-PJT

Création d'une blockchain, avec un wallet en ligne de commande et une interface web permettant de connaitre l'état de la blockchain.

## Utilisation
### Compilation
```shell
cd pjtblockchain
./mvnw package
```
### Lancement d'un noeud
```shell
java -jar pjtblockchain/node/target/node-0.0.1-SNAPSHOT.jar
```
### Interaction avec la blockchain
```shell
java -jar pjtblockchain/client/target/client-0.0.1-SNAPSHOT.jar --help
```

## Changelog
### Version 0.0.1
* Creation projet de base de blockchain et wallet
### Version 0.0.0
* Git init

## Languages utilisés
### BockChain
* Java server (Spring)

### Wallet
* CLI java app (.jar)
* Script Bash / Cmd qui lance le jar

### Front
* Angular ? 

### BO
* Java server (Spring)

## Infra 
* Tomcat8 (Inclus dans spring boot)

## Reference
* **IDE** IntellijIDEA
* **Example BlockChain in Java** [csaguil - P2P BlockChain](https://github.com/csaguil/p2p-blockchain)

## Regle de dev
* Code et commentaire en Anglais

## Idée

* **KTor** Kotlin library to create Back Office [Link](https://ktor.io/)
* **Koin** Kotlin library for dependency injection [Link](https://github.com/InsertKoinIO/koin)
* **Exposed** Kotlin library for SQL [Link](https://github.com/jetbrains/Exposed)
* **Dokka** Kotlin library to create documentation [Link](https://github.com/Kotlin/dokka)
* **Awesome Kotlin** Kotlin ressources list [Link](https://github.com/KotlinBy/awesome-kotlin)
* **Awesome Java** Java ressources list [Link](https://github.com/akullpp/awesome-java)
* **Awesome JavaEE8** Java ressources list [Link](https://github.com/hantsy/awesome-javaee8)

