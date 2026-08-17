# 🚀 AeroFlow

**An Enterprise-Grade Distributed Telemetry Ingestion Engine**

AeroFlow is a high-throughput, horizontally scalable distributed system designed to ingest, validate, and process massive volumes of telemetry data from mobile clients. Built with microservices architecture principles, it strictly decouples data ingestion from heavy backend processing using event-driven streams.

## 🏗️ System Architecture

Currently in active development. The architecture follows a strict event-driven pattern:

1. **Mobile Client** sends nested JSON telemetry payloads.
2. **Ingestion Service** acts as the API Gateway/Bouncer. It performs strict Jakarta validations and forcefully rejects malformed data (`400 Bad Request`). Valid data is acknowledged instantly (`202 Accepted`).
3. **Apache Kafka** acts as the central nervous system. The Ingestion Service acts as a Producer, securely serializing and streaming the data to the `telemetry-events` topic.
4. **Analytics Service** *(Coming Soon)* acts as the Consumer, reading events and persisting them to the database.

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.x
* **Message Broker:** Confluent Kafka (KRaft mode)
* **Database:** PostgreSQL 16
* **Caching:** Redis 7
* **Infrastructure:** Docker & Docker Compose
* **Libraries:** Lombok, Jackson (JSR-310 for Java 8 Time), Spring Boot Validation

## 📂 Monorepo Structure

```text
aeroflow/
├── docker-compose.yml         # Core infrastructure (Kafka, Postgres, Redis)
├── README.md                  # System documentation
└── ingestion-service/         # Microservice: API Front Door & Kafka Producer
    ├── src/main/java/...      # Spring Boot source code
    └── pom.xml
```

## 🚀 Getting Started (Local Development)

### Prerequisites

Docker Desktop (running)

Java 21 installed

Maven installed

### 1. Boot the Infrastructure

AeroFlow relies on containerized infrastructure to guarantee local environment reproducibility.

```bash
# Navigate to the root aeroflow directory
docker compose up -d
```

(Note: Kafka is configured in KRaft mode and will automatically elect a controller.)

### 2. Start the Ingestion Service

```bash
# Navigate to the ingestion-service directory
cd ingestion-service
mvn clean spring-boot:run
```

### 3. Verify the Kafka Stream (Terminal)

To prove data is successfully traveling through the broker, open a new terminal and attach a consumer directly to the Kafka container:

```bash
docker exec -it aeroflow-kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic telemetry-events \
  --from-beginning
```

### 4. Fire a Telemetry Event

Send a POST request to http://localhost:8080/api/v1/telemetry with the following JSON body (Content-Type: application/json):

```json
{
  "userId": "12345",
  "taskId": "task-abc-001",
  "timestamp": "2026-08-17T10:00:00Z",
  "metadata": {
    "taskType": "COMPUTE_INTENSIVE"
  },
  "metrics": {
    "currentPace": 12.5
  },
  "location": {
    "latitude": 12.9716,
    "longitude": 77.5946
  }
}
```

You will receive a 202 Accepted response, and the JSON payload will instantly appear in your Kafka terminal listener.

## 🚧 Roadmap

[x] Week 1: Core API Contract & Data Modeling (Nested DTOs).

[x] Week 2: Ingestion Service, strict validation, and Kafka KRaft Producer integration.

[ ] Week 3: Analytics Service (Kafka Consumer) & PostgreSQL Persistence (Spring Data JPA).

[ ] Week 4: Redis Caching & Rate Limiting.
