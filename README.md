# Banque Distribuée - Projet RMI & JMS

## 📌 Description

Ce projet implémente une **application bancaire distribuée** en Java utilisant :

- **RMI (Remote Method Invocation)** pour les services distants.
- **JMS (Java Message Service)** pour les notifications et opérations.
- **Architecture multi-modules Maven** :
  - `common` : Classes et interfaces partagées.
  - `server` : Implémentation du serveur RMI et logique métier.
  - `client` : Application cliente pour tester les services.

Le projet permet de gérer des clients bancaires et leurs comptes, avec des opérations telles que :

- Ajouter / supprimer / modifier un client.
- Déposer ou retirer de l’argent.
- Lister tous les clients.
- Recevoir des notifications via JMS.

---

## 🏗️ Architecture du projet

### Structure des modules Maven

DAR/
│
├─ common/ → Classes et interfaces partagées
│ ├─ src/main/java/com/banque/common/
│ │ ├─ Client.java
│ │ ├─ IBanqueService.java
│ │ └─ ServiceException.java
│ └─ pom.xml
│
├─ server/ → Serveur RMI et logique métier
│ ├─ src/main/java/com/banque/server/rmi/
│ │ ├─ BanqueRMIService.java
│ │ └─ ServerMain.java
│ ├─ src/main/java/com/banque/server/jms/
│ │ └─ JMSProducer.java
│ └─ pom.xml
│
├─ client/ → Application cliente
│ ├─ src/main/java/com/banque/client/
│ │ └─ ClientMain.java
│ └─ pom.xml



### Description des modules

| Module | Contenu |
|--------|---------|
| `common` | Contient les classes partagées et l’interface `IBanqueService`. |
| `server` | Implémente le serveur RMI et la logique métier, inclut le producteur JMS pour notifications. |
| `client` | Contient le client pour tester les services RMI et les opérations bancaires. |

---

## 🛠️ Prérequis

- **Java 8** (JDK 1.8)
- **Maven 3.x**
- IDE recommandé : IntelliJ IDEA ou Eclipse
- Serveur de messages JMS (ou simulation via `JMSProducer`)
- ---
