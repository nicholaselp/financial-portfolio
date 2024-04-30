# Financial Portfolio Service

## Table of Contents
1. [Project Specifications](#project-specifications) 
   2. [Java 17](#java-17)
   3. [Spring Boot 3.2.0](#spring-boot-3.2.0)
   4. [Maven wrapper build tool](#maven-wrapper-build-tool)
   5. [OpenAPI](#openapi)
   6. [Database](#database)
   7. [Docker](#docker)
   8. [Spring Security](#spring-security)
2. [Building the Project](#building-the-project)
3. [Debugging the Application](#debugging-the-application)
4. [Project Setup](#project-setup)
   1. [Pre-requisites](#pre-requisites)
   2. [Recommended Tools](#recommended-tools)
   3. [Postman](#postman)
5. [How to Run the Application](#how-to-run-the-application)


## Project Specifications
### Java 17
### Spring Boot 3.2.0
### Maven wrapper build tool
### OpenAPI
- This project uses OpenAPI to generate the API schema and DTOs.
- To view the APIs, refer to [financial-portfolio-api.yaml](src/main/resources/openapi/financial-portfolio-api.yaml) to view all the endpoints
- To view the generation task, refer to [pom.xml](pom.xml) - under execution with id: generate
- API mustache is used as a template to generate code for the API interface

### Database:
- Hibernate/PostGresql/Liquibase

### Cache:
- Redis is used for caching mechanism
- During application startup expenseCategories are read from the database and stored in a redisTemplate
  - See file [RedisCachingService.java](src/main/java/com/elpidoroun/financialportfolio/service/cache/RedisCachingService.java)
- Cache is used by normalizer to check/validate the expenseCategory from cache instead of going straight to the database each time
  - see file [ExpenseCategoryNormalizer.java](src/main/java/com/elpidoroun/financialportfolio/service/normalize/ExpenseCategoryNormalizer.java)
- On create/update/delete of ExpenseCategory through the repository [ExpenseCategoryRepository.java](src/main/java/com/elpidoroun/financialportfolio/repository/ExpenseCategoryRepository.java) the cache is updated through the annotations to make sure cached data are up to date
### Docker
- Application uses docker-compose file to start 2 containers for postgres and pgadmin

### Spring security
- Spring security was implemented in this project. During application startup the _user table populates two users with roles ADMIN and USER.
- There are 2 APIs in the postman collection to register a new user or get the authorization token for the 2 users
- jwt tokens are generated with an expiration of 4 hours. 
- During each API call the token is validated to see if the user is authorized to call the API
- All the APIs in postman have Authorization on their header so the APIs are called securely
- Currently ADMIN user has permissions to execute all CRUD operations on Expense and ExpenseCategory whereas USER has CRUD operations on Expense and Read on ExpenseCategory

## Building the Project
- ./mvnw clean install
- If using intelliJ you can use the saved configurations for building the project

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
