# Microservices Application: Event and Ticket Management

This repository contains a microservices-based application built with **Java**, **RabbitMQ**, **MongoDB (Compass & Atlas)**, and Spring Boot. It implements two primary services:

- **`ms-event-manager`**: Manages events.
- **`ms-ticket-manager`**: Manages tickets and validates event information via `ms-event-manager`.

## Overview
The application is designed to demonstrate a distributed architecture using microservices. Communication between services is achieved through **RabbitMQ** and REST APIs, ensuring scalability and modularity.

---

## Technologies Used

### Backend
- **Java**
- **Spring Boot**
    - Spring Web
    - Spring Data MongoDB
    - Spring Cloud OpenFeign
    - Spring AMQP (RabbitMQ)

### Messaging
- **RabbitMQ**: Used for asynchronous communication between services.

### Database
- **MongoDB Compass**: Local database visualization and management.
- **MongoDB Atlas**: Cloud-based MongoDB storage.

### Containerization
- **Docker Compose**: For running services in isolated environments.

---

## Architecture

### 1. `ms-event-manager`
Manages the lifecycle of events, including creation and retrieval. Events are stored in a MongoDB database.

**Endpoints:**
- `POST /events/create-event`: Create a new event.
- `PUT /events/update-event/{id}`: Update an event.
- `DELETE /events/delete-event/{id}`: Delete an event.
- `GET /events/get-event/{id}`: Retrieve an event by ID.
- `GET /events/get-all-events`: Get all events.
- `GET /events/get-all-events/sorted`: Get all events sorted by name.

### 2. `ms-ticket-manager`
Handles ticket creation and validates events by communicating with `ms-event-manager`. After creating a ticket, a confirmation email is sent via RabbitMQ.

**Endpoints:**
- `POST /tickets/create-ticket`: Create a new ticket.
- `DELETE /tickets/cancel-ticket/{id}`: Delete a ticket by ID.
- `DELETE /tickets/cancel-ticket-cpf/{cpf}`: Delete a ticket by CPF.
- `GET /tickets/get-ticket/{id}`: Get ticket by ID.
- `GET /tickets/get-ticket-by-cpf/{cpf}`: Get ticket by CPF.
- `GET /tickets/check-tickets-by-event/{id}`

**Validation Flow:**
1. `ms-ticket-manager` sends a request to `ms-event-manager` to validate the existence of an event.
2. If valid, the ticket is created and stored in the MongoDB database.
3. RabbitMQ queues the ticket for email confirmation.

---

## Setup and Deployment

### Prerequisites
- Java 17+
- Maven
- Docker & Docker Compose
- MongoDB Atlas account (optional for cloud database)
- RabbitMQ server

### Steps to Run Locally

#### 1. Clone the Repository
```bash
git clone <repository-url>
cd <repository-folder>
```

#### 2. Start Docker Compose
Run the following command to start all services:
```bash
docker-compose up --build
```

#### 3. Access Services
- **ms-event-manager**: `http://localhost:8080`
- **ms-ticket-manager**: `http://localhost:8081`

### MongoDB Configuration
- Use **MongoDB Compass** for local database management.
- For cloud storage, connect to **MongoDB Atlas** using the provided connection string in the `.env` file.

---

## Example `.env` File
Create a `.env` file with the following environment variables:
```properties
# Event Manager
SPRING_APPLICATION_NAME=ms-event-manager
SPRING_DATA_MONGODB_URI=mongodb+srv://<username>:<password>@cluster.mongodb.net/
SPRING_DATA_MONGODB_DATABASE=db_event
SERVER_PORT=8080

# Ticket Manager
SPRING_APPLICATION_NAME=ms-ticket-manager
SPRING_DATA_MONGODB_URI=mongodb+srv://<username>:<password>@cluster.mongodb.net/
SPRING_DATA_MONGODB_DATABASE=db_ticket
SERVER_PORT=8081

# RabbitMQ
SPRING_RABBITMQ_ADDRESSES=amqps://<username>:<password>@<host>/<vhost>
SPRING_RABBITMQ_QUEUE=ms.ticket
```

---

## Key Features
1. **Microservices Architecture**
    - Independent services for events and tickets.
    - Communication via REST and RabbitMQ.

2. **MongoDB Integration**
    - Event and ticket data are stored in separate MongoDB instances.
    - Local and cloud database support (Compass & Atlas).

3. **Asynchronous Messaging**
    - RabbitMQ ensures decoupled communication for ticket confirmation.

4. **Validation via OpenFeign**
    - `ms-ticket-manager` validates events by calling `ms-event-manager` using Feign clients.

---

## Sample Request and Response

### 1. Create Event
**Request:**
```http
POST /events/create-event
Content-Type: application/json
{
  "name": "Concert",
  "description": "Live music concert",
  "dateTime": "2025-01-15T19:00:00",
  "cep": "12345-678",
  "logradouro": "Main Street",
  "bairro": "Downtown",
  "cidade": "New York",
  "uf": "NY"
}
```
**Response:**
```json
{
  "id": "123",
  "name": "Concert",
  "description": "Live music concert",
  "dateTime": "2025-01-15T19:00:00",
  "location": {
    "cep": "12345-678",
    "logradouro": "Main Street",
    "bairro": "Downtown",
    "cidade": "New York",
    "uf": "NY"
  }
}
```

### 2. Create Ticket
**Request:**
```http
POST /tickets/create-ticket
Content-Type: application/json
{
  "eventId": "123",
  "customerName": "Nata cezer",
  "customerMail": "natacezer@example.com",
  "BRLamount": 200.0
}
```
**Response:**
```json
{
  "ticketId": "456",
  "eventId": "123",
  "customerName": "Nata cezer",
  "customerMail": "natacezer@example.com",
  "amount": {
    "BRL": 200.0,
    "USD": 50.0
  },
  "status": "pending"
}
```
