# Financial Portfolio Service

# Table of Contents
1. [Financial Portfolio Service](#financial-portfolio-service)
2. [Project Specifications](#project-specifications)
3. [Building the Project](#building-the-project)
4. [About OpenAPI](#about-openapi)
5. [Debugging the Application](#debugging-the-application)
6. [Project Setup](#project-setup)
    - [Pre-requisites](#pre-requisites)
    - [Recommended Tools](#recommended-tools)
        - [Postman](#postman)
7. [How to Run the Application](#how-to-run-the-application)


## Project Specifications
- Java 17
- Spring Boot
- Maven wrapper build tool
- OpenAPI to generate API specification and Dtos
- Database: Hibernate/PostGresql/Liquibase
- Docker

## Building the Project
./mvnw clean install

## About OpenAPI
- This project uses OpenAPI to generate the API schema and DTOs.
- To view the APIs, refer to [financial-portfolio-api.yaml](src/main/resources/openapi/financial-portfolio-api.yaml) to view all the endpoints
- To view the generation task, refer to [pom.xml](pom.xml) - under execution with id: generate
- API mustache is used as a template to generate code for the API interface

## Debugging the Application
- If you know the endpoint you want to debug, you can find it at in one of the ApiDelegate interfaces generated after building the application. You can find them here (target/generated/src/main/java/com.financialportfolio.generated.api)
- And then see where the API definition is overridden

## Project Setup

### Pre-requisites
Before running the application, make sure you have the following tools installed
- [Docker](https://www.docker.com/)

### Recommended Tools
#### Postman
- For testing and interacting with the application's APIs, [Postman](https://www.postman.com/downloads/) is recommended.
- In the repository, you can find a folder [postman_api_collection](./postman_api_collection) where you can import all the requests to run the APIs of this project

## How to Run the Application
After installing Docker you can simply run the Spring Boot Configuration "FinancialPortfolioApplication" and using the docker-compose file it will automatically start two containers. The postgres and pgadmin container.
The Application uses liquibase which will automatically create the tables required and there are volumes in place that will hold the data even if the containers are shut down.
Go to http://localhost:5050/ to view the pgAdmin UI, password is "password"
Use the collections provided in the project to call the APIs
