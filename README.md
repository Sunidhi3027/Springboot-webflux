## Spring Boot WebFlux – Reactive Employee Management API

   This project is a **Reactive REST API** built using **Spring Boot WebFlux**.  
   It demonstrates **non-blocking, asynchronous programming** using **Project Reactor (Mono & Flux)** with **MongoDB** as the database.

   
## Features

- Reactive REST APIs using Spring WebFlux
- Non-blocking data access with Reactive MongoDB
- Uses Mono and Flux for reactive streams
- Layered architecture (Controller → Service → Repository)
- Employee APIs:
  - Create Employee
  - Get All Employees
  - Get Employee by ID
  - Delete Employee
- Docker support

## Tech Stack

- Java  
- Spring Boot  
- Spring WebFlux  
- Project Reactor  
- MongoDB  
- Maven  
- Docker

## Project Structure

springboot-webflux/
│
├── controller/
│ └── EmployeeController.java
├── service/
│ ├── EmployeeService.java
│ └── EmployeeServiceImpl.java
├── repository/
│ └── EmployeeRepository.java
├── model/
│ └── Employee.java
├── config/
│ └── MongoConfig.java
├── application.yml
├── pom.xml
└── Dockerfile

