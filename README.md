# 🏨 Hotel Reservation System 

A hotel reservation system project based on  microservices architecture using Spring Cloud and Docker

## 🍃 Microservices Architecture
### This project basically consists of 9 modules:
- **Authentication Service**: This service is responsible for manage user data, generate JWT token and authenticate an user.
- **Config Service**: This service basically manage the configuration of all another services, like application.yml or .properties files, etc.
- **Discovery Service**: This service is responsible for registry all another services using Eureka Service Discovery.
- **Gateway Service**: This service is responsible for handle client requests and route them to the correct service, centralizing all requests. Spring Cloud Gateway was used here.
- **Hotel Service**: This service is responsible for handling hotel data. 
- **Notification Service**: This service is basically responsible for sending emails with important informations about reservations, payments, etc.
- **Payment Service**: This service is responsible for handling payment data.
- **Reservation Service**: This service is responsible for handling reservation data.
- **Room Service**: This service is responsible for handling room data.

## 🛢️ Databases 
Following a microservices architecture, each service in this project has its own database. The databases used by the services are PostgreSQL as SQL database and MongoDB as NOSQL database. The services **authentication-service**, **hotel-service**, **payment-service**, **reservation-service** and **room-service** used PostgreSQL as the database and FlyWay for database migrations. While **notification-service** used MongoDB as database.

## Communication between microservices
Two types of communication were used to communicate between micro services: synchronous and asynchronous. OpenFeign was used for synchronous communication, while Apache Kafka was used for asynchronous communication. **The communication scheme is:**

- Communication between any service with notification-service: Apache Kafka.
- Communication between two services that there are not notification-service: Open OpenFeign

## 📋 Requirements 

    Docker installed and running on your local machine

## ⚙️ How to start services locally with docker

    1. Clone this repository or download the zip version and unzip it
    2. Open the terminal in your application
    3. Type the command: docker-compose -f docker-compose-db.yml up -d
    4. Wait until all the containers are up and running
    5. Type the command: docker-compose -f docker-compose-services.yml up -d
    6. The application is up! http://localhost:8080

##  📄  Documentation 

Springdoc-openapi was used to document all services. You can access the individual documentation for each service by following the links below or the centralised documentation on the gateway-service at the following link: `http://localhost:8080/webjars/swagger-ui/index.html`

### 📄 Links to each service documentation: 

- `http://localhost:8000/swagger-ui/index.html` - **Authentication Service**
- `http://localhost:8010/swagger-ui/index.html` - **Hotel Service**
- `http://localhost:8020/swagger-ui/index.html` -  **Room Service**
- `http://localhost:8030/swagger-ui/index.html` -   **Reservation Service**
- `http://localhost:8040/swagger-ui/index.html` -    **Payment Service**

##  💻  Project used technologies

- Java 21 
- Spring Boot, JPA, Security and Cloud
- Docker
- PostgreSQL
- FlyWay
- MongoDB
- Apache Kafka
- OpenFeign
- Jib for docker images
- MailDev
