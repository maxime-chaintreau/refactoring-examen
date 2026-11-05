| Solid | Emplacement | Probleme |
| --- | --- | --- |
| Single responsibility | [GestionResidence](./src/main/java/fr/efrei/residence/GestionResidence.java)| Une seule classe fais l'inscription des étudiants la création des logements, payer le loyer, etc...|
| Open/ closed| [Residence](./src/main/java/fr/efrei/residence/Residence.java), [CalculateurLoyer](./src/main/java/fr/efrei/residence/CalculateurLoyer.java)| Si l'on veut ajouter un nouveau type de logement il faut modifier a plusieurs endroit pour le rendre effectif|
| Liskov subtitution| [GestionResidence](./src/main/java/fr/efrei/residence/GestionResidence.java)| Le type de logement colocation ne peut pas remplacer la classe logement dans tout les cas, c'est pourquoi il y a un instanceof dans GestionResidence|
| Interface segregation| [ILogement](./src/main/java/fr/efrei/residence/ILogement.java), [Studii](./src/main/java/fr/efrei/residence/Studio.java)| L'interface ILogement force des métodes dont les enfants n'ont pas besoin, comme getSurfaceBalcon ou Studio throw du coup une erreur|
| Dependency Inversion| [GestionResidence](./src/main/java/fr/efrei/residence/GestionResidence.java)| Gestion residence créer ses propres dépendeces avec par exemple: SmsService sms = new SmsService();|

***
***
Palier 7 : preuve par l'extension

Ajouts: type DUPLEX, option local vélo, profil SPORTIF, canal PUSH

7 nouvelles classes: Duplex, TarifDuplex, OptionLocalVelo, RegleSportif, PushService,
NotificationPush, et le test ExtensionsTest (13 tests unitaires).

Fichiers existants modifiés : 12, dont 10 ne sont que de l'« assemblage » au sens du sujet :

| Fichier | Modification | Nature |
| --- | --- | --- |
| TypeLogement, Profil, Canal, Option | 1 constante ajoutée dans chaque enum | déclaration de la nouveauté |
| FabriqueLogement | 1 ligne case DUPLEX | fabrique |
| TarifLogement.pour | 1 ligne case DUPLEX | choix de la stratégie |
| RegleProfil.pour | 1 ligne case SPORTIF | choix de la stratégie |
| CalculateurLoyer.construire | 1 ligne OptionLocalVelo après l'assurance | assemblage des décorateurs |
| Main, NouveauSystemeAdapter | ajout du canal PUSH au graphe d'objets | construction du graphe |
| ServiceNotification | les canaux sont reçus en Map<Canal, CanalNotification> au lieu de 3 paramètres | modification réalisée une seule fois car mal réalisée dans les paliers précédent, le prochain canal ne touchera plus cette classe |
| ServiceRapport | 1 ligne : un type absent du parc n'a pas de ligne de rapport | Modification réalisée une seule fois pour ne pas poser de probleme avec les test dû à l'ajout de DUPLEX |

Aucune règle métier existante n'a été modifiée
