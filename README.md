# EnerShare - Smart Energy Community Platform

EnerShare is a distributed microservices platform designed to enable local energy trading between households. It allows prosumers (producers + consumers) to sell their excess renewable energy (solar/wind) to neighbors via an automated marketplace.

## 🏗️ Architecture

The project follows **Clean Architecture** and **Domain-Driven Design (DDD)** principles. It is composed of 4 autonomous microservices:

1.  **Community Service (Port 8081):** Manages Households, Authentication, and Smart Meter definitions. (PostgreSQL)
2.  **Metering Service (Port 8082):** Ingests high-frequency IoT data from Smart Meters. (MongoDB)
3.  **Trading Service (Port 8083):** Core domain. Manages Auctions, Bids, Offers, and the Matching Engine. (PostgreSQL)
4.  **Wallet Service (Port 8084):** Manages virtual funds and listens to trade events to settle payments. (PostgreSQL)

**Communication:** Services communicate via Spring Events (Sync) and REST APIs.

## 🚀 Prerequisites

* Java 17 or 21
* Maven 3.8+
* Docker & Docker Compose

## 🛠️ Installation & Setup

### 1. Start Infrastructure
We use Docker to spin up the required databases (PostgreSQL and MongoDB).

`docker-compose up -d`

### 2 Build the project
`mvn clean install`

### Run the Microservices

# Terminal 1
java -jar community/target/community-1.0.0-SNAPSHOT.jar

# Terminal 2
java -jar metering/target/metering-1.0.0-SNAPSHOT-exec.jar

# Terminal 3
java -jar trading/target/trading-1.0.0-SNAPSHOT.jar

# Terminal 4
java -jar wallet/target/wallet-1.0.0-SNAPSHOT.jar

## ✅ Implemented Use Cases

| Domain | Feature | Status |
| :--- | :--- | :--- |
| **Community** | Register Household | ✅ Implemented |
| **Community** | Agent: Freeze/Unfreeze Account | ✅ Implemented |
| **Metering** | Ingest Meter Reading (IoT) | ✅ Implemented |
| **Trading** | Create Trading Session | ✅ Implemented |
| **Trading** | Place Bid / Place Offer | ✅ Implemented |
| **Trading** | **Matching Engine (FIFO)** | ✅ Implemented |
| **Wallet** | Add / Withdraw Funds | ✅ Implemented |
| **Wallet** | **Auto-Payment on Match** | ✅ Implemented |
