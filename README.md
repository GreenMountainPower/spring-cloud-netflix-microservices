Spring Cloud Netflix Microservices
============
This project provides a simple and basic example implementation of Spring Boot Microservices with Spring Cloud and Netflix Open Source Software components. With a few simple annotations we can quickly enable and conifgure common patterns in our applications for building distributed systems. The patterns provided in this project include Service Discovery (Eureka), Intelligent Routing (Zuul) and Client Side Load Balancing (Ribbon). This project also stands up and consumes from the Spring Cloud Config Server.

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
* eureka-server
* config-service
* zuul-service
* bus-service-client
* train-service-client

`cd` into each of the above modules (in that order) and run the following commands:
```bash
mvn clean package
mvn spring-boot:run
```

Now you should have all the services up and running. The `bus-service-client` and `train-service-client` modules are set up to be able to run multiple instannces of each. To do that, all you'd have to do is, open a new terminal, `cd` into the specific module and run `mvn spring-boot:run`

Use the PAW file available under `/paw` directory to execute the service requests.

# Documentation
As mentioned earler, the following are the concepts and patterns used in this project:
* [Netflix Eureka](https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance)
	* [Spring Netflix Service Discovery and Registration](https://spring.io/blog/2015/01/20/microservice-registration-and-discovery-with-spring-cloud-and-netflix-s-eureka)
* [Spring Cloud Config Server](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)
* [Spring Netflix Zuul Intelligent Routing](https://spring.io/guides/gs/routing-and-filtering)