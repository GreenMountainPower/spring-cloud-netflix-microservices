Spring Cloud Netflix Microservices
============
This project provides a simple and basic example implementation of Spring Boot Microservices with Spring Cloud and Netflix Open Source Software components. With a few simple annotations we can quickly enable and conifgure common patterns in our applications for building distributed systems. The patterns provided in this project include Service Discovery (Eureka), Intelligent Routing (Zuul) and Client Side Load Balancing (Ribbon).

## Requirements

* A favourite text editor or IDE
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
* [Maven 3.0+](https://maven.apache.org/download.cgi)
* You can also import the code straight into you IDE:
	* [Spring Tool Suite (STS)](https://spring.io/guides/gs/sts/)
	* [IntelliJ Idea](https://spring.io/guides/gs/intellij-idea/) 

# Development
Clone the git repository:

```bash
git clone https://github.com/GreenMountainPower/spring-cloud-netflix-microservices.git
```

After cloning the repository, import the project into the IDE.

## Running & Testing

To run the application, all the modules need to compiled and run. There are 5 modules in this project:
* bus-client-service
* train-client-service
* zuul-service
* config-service
* eureka-server

`cd` into each of the above modules and run the following commands"
```bash
mvn clean package
mvn spring-boot:run
```