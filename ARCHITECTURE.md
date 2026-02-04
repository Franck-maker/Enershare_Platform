# EnerShare - System Architecture

EnerShare is a distributed platform designed for Peer-to-Peer (P2P) energy trading. The architecture embraces a hybrid approach: **Microservices** for scalability and isolation, **Domain-Driven Design (DDD)** for business logic organization, and **Hexagonal Architecture** (Ports & Adapters) for internal service structure.

## 1. High-Level Topology (Microservices)

The system is composed of 5 autonomous services, each responsible for a specific Bounded Context.

```mermaid
graph TD
    User((User / IoT))
    
    subgraph "Core Domain"
        Trading[Trading Service]
    end
    
    subgraph "Supporting Services"
        Community[Community Service]
        Metering[Metering Service]
        Wallet[Wallet Service]
        Notif[Notification Service]
    end
    
    User -->|HTTP Requests| Community
    User -->|HTTP Requests| Trading
    User -->|IoT Data| Metering
    
    %% Communication Flow
    Trading -->|Verify Production| Metering
    Trading -->|Execute Payment| Wallet
    Trading -->|Send Alerts| Notif
    Trading -.->|Verify User Role| Community
```

### Services Description
| Service | Pattern Role | Responsibility | Tech Stack |
| :--- | :--- | :--- | :--- |
| **Trading** | **Core Domain** | Managing Auctions, Bids, Offers, and the Matching Engine. | Java 17, Spring Boot, PostgreSQL |
| **Wallet** | Generic Subdomain | Managing virtual funds, Locking funds, Executing transfers. | Java 17, Spring Boot, PostgreSQL |
| **Metering** | Generic Subdomain | High-volume ingestion of Smart Meter data (IoT). | Java 17, Spring Boot, **MongoDB** |
| **Community** | Supporting Subdomain | User Identity (Households), Addresses, smart meter mapping. | Java 17, Spring Boot, PostgreSQL |
| **Notifications** | Generic Subdomain | Delivering async messages to users. | Java 17, Spring Boot |

---

## 2. Internal Structure (Hexagonal / Clean Architecture)

All microservices in the platform follow a consistent **Hexagonal Architecture** (Ports & Adapters) structure to ensure modularity and extensive separation of concerns. This ensures that even simpler services are ready for future complexity.

### Standard Layering Across Services

Every service (`Trading`, `Community`, `Wallet`, `Metering`) is organized into these four distinct layers:

```text
src/main/java/com/enershare/{service}/
├── api/                  <-- [Primary Adapter] REST Controllers
├── application/          <-- [Port] Input Ports (Use Cases / Application Services)
├── domain/               <-- [Core] Entities & Business Rules
└── infrastructure/       <-- [Secondary Adapter] Repositories & External Gateways
```

### Why this consistency?
1.  **Scalability**: Even "simple" services like `Community` can evolve complex rules (e.g., identity verification) without refactoring the structure.
2.  **Cognitive Load**: Developers can switch between services easily as they all share the same architectural map.
3.  **Isolation**: The Domain is always protected from dependencies, whether it's PostgreSQL in `Wallet` or MongoDB in `Metering`.

### Deep Dive: The Trading Service
While all services share the structure, `Trading` leverages it most heavily for the **Matching Engine**:
*   The `TradingEngineService` (Application) implements the core logic.
*   It defines interfaces for `WalletGateway` and `EnergyGateway`.
*   The `Infrastructure` layer provides the Implementation (Rest Clients).

## 3. Key Workflows

### A. The Trading Loop (Step-by-Step)
1.  **Offer/Bid Creation**: Users submit orders via REST API.
2.  **Session Closing**: The generic "Matching Engine" is triggered.
3.  **Matching Logic**: The system finds compatible Bids and Offers (Price/Quantity).
4.  **Energy Validation** *(Inter-service)*: Calls `Metering` to ensure the seller produced energy.
5.  **Fund Check & Lock** *(Inter-service)*: Calls `Wallet` to ensure the buyer has funds.
6.  **Transaction Execution**: 
    *   Records `Trade` in local DB (History).
    *   Calls `Wallet` to transfer money.
7.  **Notification**: Calls `Notification` to alert users.

---

## 4. Current Status & Roadmap

- [x] **Microservices Skeleton**: All services up and running in Docker.
- [x] **Trading Logic**: Matching engine functional with partial matches.
- [x] **Payments**: Wallet integration for real-time transfers.
- [x] **Gateways**: Infrastructure decoupled from Domain.
- [x] **Roles Management**: Enforcing "Prosumer" vs "Consumer" constraints.
