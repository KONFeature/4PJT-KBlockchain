# 4-PJT
## Blockchain  

Une fois executer, vous pourrez acceder à la webapp via "localhost:port" (le port étant celui que vous avez renseigner dans le fichier blockchain.properties)

### Execution  
A la racine du projet :   

     ./gradlew bootRun  

> Si vous rencontrez des erreurs, assurez vous d'avoir le fichier 'blockchain.properties' à la racine du projet  


### Compilation  
A la racine du projet :   

     ./gradlew bootJar  

Cette commande va generer un fichier jar executale dans le dossier "build/libs/"

### Properties
blockchain.properties : 
> wallet.key.path -> Emplacement du keystore par apport à la blockchain
> server.port -> Port qui seras utiliser pour la communication entre la blockchain et le wallet / la webapp
> p2p.accessible.host -> IP qui seras envoyé aux autres noeuds pour communiquer avec cette instance de la blockchain
> p2p.port -> Port utiliser par l'application pour communiquer avec les autres noeuds
> blockchain.known.node.host -> IP d'un noeud connue de la blockchain
> blockchain.known.node.port -> Port d'un noeud connue de la blockchain
> blockchain.db.name -> Nom du fichier de base de donné

## Wallet

Pour le detail des commandes, vous pouvez faire sur le wallet : 

    help
    help nomDeLaCommande

Ou sinon lire la documentation utilisateur

### Execution  
A la racine du projet :   

     ./gradlew bootRun  

### Compilation  
A la racine du projet :   

     ./gradlew bootJar  

Cette commande va generer un fichier jar executale dans le dossier "build/libs/"
## WebApp
### Compilation  
A la racine du projet :   

     ng build --prod
     
Vous n'aurez plus qu'a lancer la blockchain pour acceder au site web
