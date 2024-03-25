# Financial Portfolio Service
## About

## Project Specifications
- Java 17
- Maven wrapper build tool
- OpenAPI to generate API specification and Dtos
- Hibernate
- Liquibase
- Jenkin

## Building the project
./gradlew clean install

## About openAPI
- This project uses openAPI to generate the API schema and dtos. 
- To view the APIs refer to [financial-portfolio-api.yaml](src/main/resources/openapi/financial-portfolio-api.yaml) to view all the endpoints
- To view the generation task refer to [pom.xml](pom.xml) - under execution with id: generate
- API mustache is used as a template to generate code for the API interface

## Debugging the application
- If you know the endpoint you want to debug you can find it at in one of the ApiDelegate interfaces generated after building the application. You can find them here (target/generated/src/main/java/com.financialportfolio.generated.api)
- And then see where the API definition is overriden

## Database
- This project uses liquibase to generate tables.
- Currently on application startup and liquibase kicks in and creates the table with the changeSet
- on application termination all tables related to liquibase insertions are deleted and new ones are created without data during startup
