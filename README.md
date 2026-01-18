# EnerShare - Smart Energy Community Platform

EnerShare is a distributed microservices platform designed to enable local energy trading between households. It allows prosumers (producers + consumers) to sell their excess renewable energy (solar/wind) to neighbors via an automated marketplace.

## 🏗️ Architecture

The project follows **Clean Architecture** and **Domain-Driven Design (DDD)** principles. It is composed of 4 autonomous **microservices**:

1.  **Community Service (Port 8081):** Manages Households, Authentication, and Smart Meter definitions. (PostgreSQL)
2.  **Metering Service (Port 8082):** Ingests high-frequency IoT data from Smart Meters. (MongoDB)
3.  **Trading Service (Port 8083):** Core domain. Manages Auctions, Bids, Offers, and the Matching Engine. (PostgreSQL)
4.  **Wallet Service (Port 8084):** Manages virtual funds and listens to trade events to settle payments. (PostgreSQL)
5.  **Notifications Service (Port 8085):** Manages alerts and notifications.

**Communication:** Services communicate via REST APIs and Docker networking.

## 🚀 Prerequisites

* Java 17
* Maven 3.8+
* Docker & Docker Compose

## 🛠️ Installation & Setup

### 1. Build and Run (Containerized)
We use Docker Compose to build the jars and spin up the whole platform.

```bash
docker-compose up --build
```

Wait until all services are up.

### Access Points
*   **Community API**: http://localhost:8081
*   **Metering API**: http://localhost:8082
*   **Trading API**: http://localhost:8083
*   **Wallet API**: http://localhost:8084
*   **Notifications API**: http://localhost:8085

## ✅ Implemented Use Cases

| Domain | Feature | Status |
| :--- | :--- | :--- |
| **Community** | Register Household | ✅ Implemented |
| **Trading** | Create Session | ✅ Implemented |
| **Trading** | Matching Engine | ✅ Implemented |
| **Community** | Role Management (Consumer/Prosumer) | ✅ Implemented |
| **Community** | Agent: Freeze/Unfreeze Account | ✅ Implemented |
| **Metering** | Ingest Meter Reading (IoT) | ✅ Implemented |

## 🧪 Testing (Postman)

A complete end-to-end scenario is provided in the file `EnerShareAPI.postman_collection.json`.

**How to run the test:**
1.  Import the JSON file into Postman.
2.  Ensure the Docker stack is running (`docker-compose up -d --build`).
3.  Run the collection. It will automatically:
    *   Create a Prosumer (Franck) and a Consumer (Ralph).
    *   Fund their wallets.
    *   Attempt an authorized sale (Success) and an unauthorized sale (Forbidden).
    *   Execute a trade and verify the implementation.
| **Trading** | Create Trading Session | ✅ Implemented |
| **Trading** | Place Bid / Place Offer | ✅ Implemented |
| **Trading** | **Matching Engine (FIFO)** | ✅ Implemented |
| **Wallet** | Add / Withdraw Funds | ✅ Implemented |
| **Wallet** | **Auto-Payment on Match** | ✅ Implemented |
