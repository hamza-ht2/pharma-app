# Pharma Distribution App

B2B pharmacy ordering and delivery platform for a medicine distributor (grossiste-répartiteur). Replaces phone calls, WhatsApp orders, paper delivery notes, and manual driver assignment with one digital system.

## What it does

- **Pharmacists** browse a catalog and place orders
- **Distributor admins** confirm orders, manage stock, assign drivers
- **Drivers** deliver and show a one-time QR code
- **QR proof of delivery** — pharmacist scans, stock is deducted, order closed

Stock is batch-aware with expiry tracking (FEFO — first expired, first out) so the oldest medicine ships first and recalls are traceable.

## Tech stack

| Layer | Tech |
|---|---|
| Backend | Spring Boot 4.1.1, Java 23, Maven |
| Database | PostgreSQL 18 |
| Migrations | Flyway |
| Auth | Spring Security + JWT |
| Validation | Jakarta Bean Validation |
| Boilerplate | Lombok |
| Frontends (planned) | React Native (pharmacist + driver), React/Next.js (admin) |

## Architecture

Monolith. Three interfaces, one backend.

```
Admin dashboard (web)  ─┐
Pharmacist app (RN)    ─┼──►  Spring Boot REST API  ──►  PostgreSQL
Driver app (RN)        ─┘
```

**Design principles:**

- Stock-aware from day one — orders reserve stock, delivery deducts it
- Batch + expiry tracking included from day one (pharma compliance, FEFO, recall)
- QR proof of delivery uses a one-time signed JWT token, not a static QR
- `stock_movements` is an append-only audit ledger — every stock change is recorded
- REST is the source of truth; WebSocket is planned for real-time status only

## Domain model

| Entity | Purpose |
|---|---|
| `User` | Admin, pharmacist, or driver |
| `Pharmacy` | A pharmacy owned by a pharmacist |
| `Category` | Hierarchical product categories |
| `Product` | A medicine (name, dosage, form, barcode) |
| `ProductBatch` | Physical stock: batch number, expiry, quantities |
| `StockMovement` | Append-only ledger of every stock change |
| `Order` + `OrderItem` | A pharmacist's request |
| `Delivery` + `DeliveryItem` | A driver's trip and what shipped |
| `DeliveryConfirmation` | QR token + scan proof |

## The flow

```
1. Pharmacist places order
   → FEFO stock reserved on batches
   → ORDER_RESERVED movements written
   → Order status = PENDING

2. Admin confirms
   → Order status = CONFIRMED

3. Admin assigns driver
   → Delivery created (ASSIGNED)
   → QR token generated (48h expiry)
   → Order status = ASSIGNED

4. Driver marks pickup
   → FEFO batches picked, DeliveryItems created
   → Delivery status = PICKED_UP

5. Driver marks out for delivery
   → Delivery status = OUT_FOR_DELIVERY

6. Pharmacist scans QR
   → Token validated (signature, expiry, unused, correct pharmacy)
   → quantityOnHand and quantityReserved deducted
   → DELIVERED movements written
   → Order status = DELIVERED / PARTIALLY_DELIVERED
```

## Stock accounting

Two quantities on every batch:

| Field | Meaning |
|---|---|
| `quantityOnHand` | Physically in the warehouse |
| `quantityReserved` | Spoken for by pending orders |
| **available** | `quantityOnHand - quantityReserved` — what can still be sold |

**Rule:** reserve at placement, deduct at delivery confirmation. Stock leaves the warehouse only when the pharmacist scans the QR.

Every change writes a `StockMovement` row with a reason:

| Reason | Effect |
|---|---|
| `ORDER_RESERVED` | `quantityReserved += change` |
| `ORDER_RELEASED` | `quantityReserved -= change` |
| `DELIVERED` | `quantityOnHand -= change`, `quantityReserved -= change` |
| `MANUAL_ADJUSTMENT` | `quantityOnHand += change` |
| `RETURN` | `quantityOnHand += change` |
| `EXPIRED` | `quantityOnHand -= change` |

## Getting started

### Prerequisites

- Java 23
- Maven 3.9+
- PostgreSQL 18

### Database setup

```sql
CREATE DATABASE pharma_db;
CREATE USER user_pharma WITH PASSWORD 'pharma_test';
GRANT ALL PRIVILEGES ON DATABASE pharma_db TO user_pharma;
```

### Configuration

`src/main/resources/application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/pharma_db
spring.datasource.username=user_pharma
spring.datasource.password=pharma_test
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
jwt.secret=change-me-to-a-long-random-string-at-least-32-chars
jwt.expiration=86400000
jwt.qr-expiration=172800000
```

### Run

```bash
./mvnw spring-boot:run
```

Flyway applies migrations on startup. Hibernate validates the schema. App listens on `http://localhost:8080`.

## API overview

| Group | Base path | Auth |
|---|---|---|
| Auth | `/api/auth` | Public (`/login`), authenticated (`/me`) |
| Users | `/api/users` | ADMIN (management), authenticated (self) |
| Pharmacies | `/api/pharmacies` | ADMIN, PHARMACIST (own pharmacy) |
| Categories | `/api/categories` | ADMIN (writes), any authenticated (reads) |
| Products | `/api/products` | ADMIN (writes), ADMIN + PHARMACIST (reads) |
| Batches | `/api/product-batches` | ADMIN (writes), ADMIN + PHARMACIST (reads) |
| Stock movements | `/api/stock-movements` | ADMIN (read-only) |
| Orders | `/api/orders` | PHARMACIST (place/cancel), ADMIN (confirm/reject) |
| Deliveries | `/api/deliveries` | ADMIN (assign), DRIVER (status), PHARMACIST (confirm) |

## Authentication

JWT Bearer token. Login:

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@pharma.ma",
  "password": "Admin@1234"
}
```

Response contains a token. Send it in every subsequent request:

```http
Authorization: Bearer <token>
```

## Migrations

Flyway owns the schema. Files in `src/main/resources/db/migration/`:

```
V1__create_users.sql
V2__create_pharmacies.sql
V3__create_categories.sql
V4__create_products.sql
V5__create_product_batches.sql
V6__create_stock_movements.sql
V7__create_orders.sql
V8__create_order_items.sql
V9__create_deliveries.sql
V10__create_delivery_items.sql
V11__create_delivery_confirmations.sql
```

Hibernate runs in `validate` mode — it never alters the schema, only checks that entities match.

**Never edit an applied migration.** Add a new one.

## Project layout

```
src/main/java/com/marshall/pharma_app/
├── config/         # Beans, seeders
├── controller/     # REST endpoints
├── dto/
│   ├── request/    # Incoming payloads
│   └── response/   # Outgoing payloads
├── exceptions/     # Custom exceptions + global handler
├── mapper/         # Entity ↔ DTO (static)
├── models/         # JPA entities
│   └── enums/
├── repositories/   # Spring Data JPA
├── security/       # JWT filter, config, user details
└── service/
    └── impl/
```

## Status

| Phase | Scope | Status |
|---|---|---|
| 0 | Setup (Spring Boot, PostgreSQL, Flyway) | ✅ |
| 1 | Models, migrations, auth, REST, QR, 3 apps | 🚧 API complete |
| 2 | Returns, invoices, credit limits, reports, alerts | ⬜ |
| 3 | Multi-warehouse, routing, ERP, SaaS | ⬜ |

## Roadmap

- [x] 11 entities + migrations
- [x] JWT auth
- [x] Global exception handling
- [x] CRUD: users, pharmacies, categories, products, batches
- [x] Order flow with FEFO stock reservation
- [x] Delivery flow with QR proof of delivery
- [ ] Admin dashboard (React/Next.js)
- [ ] Pharmacist app (React Native)
- [ ] Driver app (React Native)
- [ ] WebSocket status updates

## License

Private. All rights reserved.
