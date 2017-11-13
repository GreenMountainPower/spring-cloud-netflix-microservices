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

Once you have all the servers up and running, point the browser to `http://localhost:8761/` to monitor the registry/dashboard. 

Use the PAW file available under `/paw` directory to execute the service requests.

# Documentation
As mentioned earler, the following are the concepts and patterns used in this project:
* [Netflix Eureka](https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance)
	* [Spring Netflix Service Discovery and Registration with Load Balancing](https://spring.io/blog/2015/01/20/microservice-registration-and-discovery-with-spring-cloud-and-netflix-s-eureka)
* [Spring Cloud Config Server](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)
* [Spring Netflix Zuul Intelligent Routing](https://spring.io/guides/gs/routing-and-filtering)

## Setting up Eureka Server

The module `eureka-server` startsup an instance of a Eureka Registry. This is achieved by including the below dependency in the `pom.xml`:
```bash
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
		</dependency>
```
and having the `@EnableEurekaServer` annotaion on the Spring configuration class, as below:

```bash
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
```

The below entries in the `src/main/resources/application.yml` simply tells this instance to not register itself with the Eureka instance it finds, because that instance is.. itself.
```bash
eureka:
  client:
    registerWithEureka: false
    fetchRegistry: false
    server:
      waitTimeInMsWhenSyncEmpty: 0
```

## Setting up Eureka Client(s)
All the clients are identified by the Eureka Server by the property `spring.application.name` in the `src/main/resources/bootstrap.yml` file. The modules `bus-service-client`, `train-service-client`, and `zuul-server` are set up as Eureka Clients. To be identifiable as a client, simply include the dependency:
```bash
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
```

and mark the Spring configuration class with the annotation `@EnableEurekaClient`:
```bash
@SpringBootApplication
@EnableEurekaClient
public class BusClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusClientApplication.class, args);
	}
}
```

The `server.port` property for the "client" modules are set to 0 to facilitate starting up multiple instances. Bening able to run multiple instances of a service opens up the gateway for setting up Load Balancing. To enable client side load balancing, the below dependency is added:
```bash
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-ribbon</artifactId>
		</dependency>
```
Netflix has a Eureka-aware client-side load-balancing client called [Ribbon](https://github.com/Netflix/ribbon) that Spring Cloud integrates extensively. Ribbon is a client library with built-in software load balancers.

To demonstrate, service to service communication, in this case, between the `bus-service-client` and `train-service-client`, Spring Cloud Feign Integration is used. [Feign](https://github.com/Netflix/feign) is a handy project from Netflix that lets you describe a REST API client declaratively with annotations on an interface. This integration makes it simple to create smart, Eureka-aware REST clients that uses Ribbon for client-side load-balacing to pick an available service instance.