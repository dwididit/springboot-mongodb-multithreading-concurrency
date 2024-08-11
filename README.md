# Spring Boot MongoDB Multithreading Concurrency

## Project Description
This project demonstrates a Spring Boot application with MongoDB integration, focusing on multithreading and concurrency.

## Technologies Used
- Java 21
- Spring Boot
- MongoDB
- Maven
- JMeter

## Setup Instructions
1. Clone the repository:
    ```sh
    gh repo clone dwididit/springboot-mongodb-multithreading-concurrency
    cd springboot-mongodb-multithreading-concurrency/
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Thread Pool Configuration
The application uses a custom thread pool configuration for asynchronous task execution:
- **Core Pool Size**: 8
- **Max Pool Size**: 16
- **Queue Capacity**: 1000


## Running Tests
To run the tests, use the following command:
```sh
mvn test