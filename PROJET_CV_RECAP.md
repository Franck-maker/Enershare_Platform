# EnerShare Platform - Récapitulatif Projet pour CV

## 📋 Vue d'ensemble du projet

**EnerShare** est une plateforme innovante de trading d'énergie pair-à-pair (P2P) qui permet aux producteurs locaux d'énergie renouvelable (prosumers) de vendre leur surplus d'énergie solaire ou éolienne à leurs voisins via un marché automatisé décentralisé.

### Contexte et Objectif
Le projet vise à démocratiser le commerce d'énergie verte au niveau local en créant une place de marché où les particuliers équipés de panneaux solaires peuvent échanger directement leur production excédentaire, favorisant ainsi l'économie circulaire et la transition énergétique.

---

## 🏗️ Architecture et Technologies

### Architecture Microservices Distribuée
Le projet implémente une architecture moderne basée sur 5 microservices autonomes, chacun responsable d'un domaine métier spécifique :

1. **Community Service** (Port 8081)
   - Gestion des utilisateurs et authentification
   - Gestion des rôles (Consommateur/Prosumer)
   - Mapping avec les compteurs intelligents

2. **Metering Service** (Port 8082)
   - Ingestion de données IoT haute fréquence
   - Suivi de la production/consommation énergétique en temps réel
   - Validation des quantités d'énergie produites

3. **Trading Service** (Port 8083) - **DOMAINE CŒUR**
   - Gestion des sessions de trading
   - Moteur de matching (enchères/offres)
   - Orchestration des transactions
   - Algorithme FIFO de correspondance automatique

4. **Wallet Service** (Port 8084)
   - Gestion de portefeuille virtuel
   - Verrouillage et transfert de fonds
   - Règlement automatique post-transaction

5. **Notifications Service** (Port 8085)
   - Alertes et notifications utilisateurs

### Stack Technique Complète

**Backend & Frameworks :**
- **Java 17** - Langage principal avec les dernières fonctionnalités (Records, Pattern Matching)
- **Spring Boot 3.2.1** - Framework applicatif moderne
- **Maven** - Gestion de dépendances et build
- **Project Lombok** - Réduction du code boilerplate

**Bases de Données :**
- **PostgreSQL 15** - Base relationnelle pour Community, Trading, et Wallet
- **MongoDB 6.0** - Base NoSQL pour les données IoT haute fréquence du Metering

**Conteneurisation & Orchestration :**
- **Docker** - Containerisation des services
- **Docker Compose** - Orchestration multi-conteneurs
- Architecture réseau isolée avec volumes persistants

**Communication :**
- **REST APIs** - Communication synchrone entre microservices
- **RestTemplate/WebClient** - Clients HTTP pour communication inter-services

---

## 🎯 Patterns et Principes Architecturaux Avancés

### 1. Clean Architecture (Architecture Hexagonale)
Chaque microservice suit rigoureusement l'architecture hexagonale (Ports & Adapters) :

```
service/
├── api/              → [Adaptateur Primaire] Contrôleurs REST
├── application/      → [Port d'entrée] Use Cases & Services applicatifs
├── domain/           → [Cœur] Entités & Règles métier
└── infrastructure/   → [Adaptateur Secondaire] Repositories & Gateways externes
```

**Avantages appliqués :**
- Isolation complète du domaine métier
- Testabilité accrue (mocking des adapters)
- Flexibilité technologique (changement de BDD sans impact sur le domaine)

### 2. Domain-Driven Design (DDD)
- **Bounded Contexts** : Séparation claire des domaines métier
- **Ubiquitous Language** : Vocabulaire métier partagé (Bid, Offer, Trade, Prosumer)
- **Aggregate Roots** : TradingSession comme racine d'agrégat
- **Value Objects** : Encapsulation des concepts métier

### 3. Patterns de Communication
- **Gateway Pattern** : Abstraction des appels externes (WalletGateway, EnergyGateway)
- **Repository Pattern** : Abstraction de la persistance
- **DTO Pattern** : Séparation entités domaine / objets de transfert

---

## 💡 Fonctionnalités Clés Implémentées

### Workflow Complet de Trading
1. **Enregistrement utilisateurs** avec rôles (Consumer/Prosumer)
2. **Approvisionnement de wallet** (ajout/retrait de fonds)
3. **Ingestion de données IoT** depuis compteurs intelligents
4. **Création de sessions de trading** avec fenêtre temporelle
5. **Placement d'offres** (vente) et de **demandes** (achat)
6. **Moteur de matching automatique** :
   - Algorithme FIFO (First In, First Out)
   - Correspondance prix/quantité
   - Support des correspondances partielles
7. **Validation multi-étapes** :
   - Vérification de la production réelle (appel Metering)
   - Vérification des fonds (appel Wallet)
8. **Exécution de transaction** avec verrouillage de fonds
9. **Règlement automatique** post-transaction
10. **Notifications** aux parties prenantes

### Sécurité et Contraintes Métier
- **Contrôle d'accès basé sur les rôles** : Seuls les Prosumers peuvent vendre
- **Gestion de gel/dégel de compte** par les agents
- **Validation de production réelle** : Impossibilité de vendre plus que produit
- **Atomicité des transactions** : Garantie de cohérence des paiements

---

## 🚀 Compétences Techniques Acquises

### Architecture & Design
✅ Conception et implémentation d'une architecture microservices complète  
✅ Application pratique de Clean Architecture et DDD  
✅ Design de systèmes distribués avec communication inter-services  
✅ Séparation des préoccupations (Separation of Concerns)  
✅ Modélisation de domaines métier complexes  

### Développement Backend Java
✅ Maîtrise de **Java 17** et ses fonctionnalités modernes  
✅ Framework **Spring Boot 3.x** (Spring Data JPA, Spring Web)  
✅ Gestion de projets multi-modules avec **Maven**  
✅ Développement d'APIs RESTful robustes  
✅ Programmation orientée objet avancée  
✅ Gestion des transactions et de la persistance  

### Bases de Données
✅ Modélisation relationnelle avec **PostgreSQL**  
✅ Requêtes JPA/Hibernate avancées  
✅ Base NoSQL **MongoDB** pour données haute fréquence  
✅ Stratégies de persistance polyglotte (multi-database)  
✅ Gestion de schémas et migrations  

### DevOps & Conteneurisation
✅ Containerisation complète avec **Docker**  
✅ Orchestration multi-services avec **Docker Compose**  
✅ Configuration d'environnements isolés  
✅ Gestion de volumes et réseaux Docker  
✅ Build et déploiement automatisés  

### Intégration & Communication
✅ Design et implémentation d'APIs REST  
✅ Communication inter-microservices (synchrone)  
✅ Pattern Gateway pour appels externes  
✅ Gestion d'erreurs et résilience  
✅ Testing avec **Postman** (collection end-to-end)  

### Patterns & Best Practices
✅ Repository Pattern  
✅ Gateway Pattern  
✅ DTO Pattern  
✅ Dependency Injection  
✅ SOLID Principles  
✅ Separation of Concerns  

---

## 📊 Résultats et Réalisations

### Réalisations Techniques
- ✅ **5 microservices fonctionnels** déployés en Docker
- ✅ **Moteur de matching automatique** avec algorithme FIFO
- ✅ **Système de paiement intégré** avec règlement automatique
- ✅ **Pipeline de données IoT** haute fréquence
- ✅ **Isolation complète des domaines** métier
- ✅ **Tests end-to-end** complets (Postman collection)

### Complexité Gérée
- Coordination de 5 services autonomes
- Gestion de 2 types de bases de données (SQL + NoSQL)
- Workflow transactionnel multi-étapes
- Validation de contraintes métier distribuées
- Communication inter-services avec gestion d'erreurs

### Démontrabilité
- Projet entièrement dockerisé : démarrage en une commande
- Collection Postman documentée pour démonstration
- Architecture claire et documentée (ARCHITECTURE.md)
- Code structuré selon les standards industriels

---

## 🎓 Apprentissages et Compétences Transversales

### Soft Skills Développées
- **Autonomie** : Conception et implémentation complète d'un système distribué
- **Architecture logicielle** : Prise de décisions architecturales justifiées
- **Résolution de problèmes** : Débogage dans un environnement distribué
- **Documentation** : Production de documentation technique claire
- **Rigueur** : Application stricte de patterns et principes

### Vision Système
- Compréhension des compromis architecture monolithique vs microservices
- Maîtrise des problématiques de communication distribuée
- Sensibilisation aux défis de la cohérence des données distribuées
- Réflexion sur la scalabilité et la résilience

---

## 📝 Comment Présenter sur un CV

### Titre de Projet
**"EnerShare - Plateforme de Trading d'Énergie P2P"**  
*Système distribué de marketplace énergétique locale*

### Description Courte (pour CV)
```
Conception et développement d'une plateforme microservices de trading 
d'énergie pair-à-pair permettant l'échange local d'énergie renouvelable.
Architecture de 5 services autonomes (Java 17, Spring Boot 3, PostgreSQL, 
MongoDB) orchestrés via Docker. Implémentation d'un moteur de matching 
automatique, système de paiement intégré et pipeline IoT temps réel.
Application de Clean Architecture, DDD et patterns avancés.
```

### Points Clés pour Bullet Points
- Développement d'une **architecture microservices** complète (5 services) en **Java 17** et **Spring Boot 3**
- Implémentation de **Clean Architecture** et **Domain-Driven Design** avec séparation stricte des couches
- Conception d'un **moteur de matching** automatique (algorithme FIFO) pour marketplace énergétique
- Intégration **polyglotte** : **PostgreSQL** (données transactionnelles) + **MongoDB** (IoT haute fréquence)
- **Containerisation Docker** et orchestration **Docker Compose** de l'écosystème complet
- Communication **REST inter-services** avec patterns Gateway et gestion de résilience
- Développement d'un **système de paiement automatisé** avec gestion de wallet et règlement transactionnel

### Mots-Clés pour ATS (Applicant Tracking Systems)
Java 17, Spring Boot, Microservices, Clean Architecture, Domain-Driven Design (DDD), 
Hexagonal Architecture, PostgreSQL, MongoDB, Docker, Docker Compose, REST API, 
Maven, IoT, Event-Driven, Backend Development, System Design, Distributed Systems

---

## 🔗 Liens et Démonstration

### Ressources du Projet
- **Repository GitHub** : [Lien vers le repo]
- **Documentation Architecture** : ARCHITECTURE.md
- **Collection Postman** : EnershareAPI.postman_collection.json
- **Diagramme Architecture** : SoftwareArchitecture.png

### Pour Démonstration Technique
```bash
# Démarrage complet en une commande
docker-compose up --build

# Accès aux services
Community API:     http://localhost:8081
Metering API:      http://localhost:8082
Trading API:       http://localhost:8083
Wallet API:        http://localhost:8084
Notifications API: http://localhost:8085
```

### Scénario de Démo (5 minutes)
1. Importer collection Postman
2. Lancer `docker-compose up --build`
3. Exécuter la collection : démonstration automatique du workflow complet
4. Montrer les logs Docker pour visualiser la communication inter-services
5. Accéder aux APIs pour montrer la structure des endpoints

---

## 💼 Pertinence pour Recruteurs

### Types de Postes Ciblés
- **Développeur Backend Java/Spring**
- **Ingénieur Microservices**
- **Software Engineer**
- **Développeur Full Stack** (côté backend)
- **Architecte Logiciel Junior**

### Pourquoi ce Projet est Convaincant
✅ **Projet complet** : Pas un simple CRUD, mais un système distribué fonctionnel  
✅ **Complexité réelle** : Gestion de transactions, communication inter-services, IoT  
✅ **Best Practices** : Application rigoureuse de patterns reconnus (Clean Arch, DDD)  
✅ **Technologies actuelles** : Stack moderne (Java 17, Spring Boot 3, Docker)  
✅ **Démontrabilité** : Projet déployable et testable immédiatement  
✅ **Documentation** : Code et architecture bien documentés  
✅ **Cas d'usage réel** : Problématique contemporaine (transition énergétique)  

---

## 📌 Conseil de Présentation en Entretien

### Structure Recommandée (5-10 minutes)
1. **Contexte** (30s) : "J'ai développé une plateforme de trading d'énergie P2P..."
2. **Défis techniques** (2min) : Architecture microservices, coordination, transactions...
3. **Solutions apportées** (3min) : Clean Architecture, patterns, technologies choisies...
4. **Résultats** (1min) : Système fonctionnel, démo Postman, apprentissages...
5. **Démo courte** (2-3min) : Montrer l'application en action

### Questions Anticipées
- "Pourquoi des microservices ?" → Isolation domaines, scalabilité, technologies adaptées
- "Difficultés rencontrées ?" → Communication inter-services, cohérence distribuée, debug
- "Améliorations futures ?" → Event sourcing, CQRS, API Gateway, Service mesh, monitoring

---

## 🎯 Conclusion

Ce projet démontre :
- Une **maîtrise technique** de l'écosystème Java/Spring moderne
- Une **compréhension architecturale** des systèmes distribués
- Une **capacité à implémenter** des patterns avancés (Clean Arch, DDD)
- Une **vision produit** avec un cas d'usage concret et pertinent
- Une **rigueur professionnelle** dans l'organisation du code

**Points forts pour un employeur :**
Un développeur capable de concevoir, architecturer et implémenter un système 
complexe de bout en bout, en appliquant les standards de l'industrie et en 
produisant un code maintenable et évolutif.
