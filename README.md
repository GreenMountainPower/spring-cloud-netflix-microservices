Spring Cloud Netflix Microservices
============
This project provides a simple and basic example implementation of Spring Boot Microservices with Spring Cloud and Netflix Open Source Software components. With a few simple annotations we can quickly enable and configure common patterns in our applications for building distributed systems. The patterns provided in this project include Service Discovery (Eureka), Intelligent Routing (Zuul) and Client Side Load Balancing (Ribbon). This project also stands up and consumes from the Spring Cloud Config Server.

## Requirements

* A favorite text editor or IDE
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

Now you should have all the services up and running. The `bus-service-client` and `train-service-client` modules are set up to be able to run multiple instances of each. To do that, all you'd have to do is, open a new terminal, `cd` into the specific module and run `mvn spring-boot:run`

Once you have all the servers up and running, point the browser to `http://localhost:8761/` to monitor the registry/dashboard. 

Use the PAW file available under `/paw` directory to execute the service requests.

# Documentation
As mentioned earlier, the following are the concepts and patterns used in this project:
* [Netflix Eureka](https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance)
	* [Spring Netflix Service Discovery and Registration with Load Balancing](https://spring.io/blog/2015/01/20/microservice-registration-and-discovery-with-spring-cloud-and-netflix-s-eureka)
* [Spring Netflix Zuul Intelligent Routing](https://spring.io/guides/gs/routing-and-filtering)
* [Spring Cloud Config Server](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)


## Setting up Eureka Server

The module `eureka-server` starts up an instance of a Eureka Registry. This is achieved by including the below dependency in the `pom.xml`:
```bash
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
		</dependency>
```
and having the `@EnableEurekaServer` annotation on the Spring configuration class, as below:

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
All the clients are identified by the Eureka Server by the property `spring.application.name` in the `src/main/resources/bootstrap.yml` file. The modules `bus-service-client`, `train-service-client`, and `zuul-server` are set up as Eureka Clients. To be identifiable as a client, this dependency is included:
```bash
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
```

and the Spring configuration class is marked with the annotation `@EnableEurekaClient`:
```bash
@SpringBootApplication
@EnableEurekaClient
public class BusClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusClientApplication.class, args);
	}
}
```

The `src/main/resources/application.yml` file also has the below entry 

```bash
eureka:
  client:
    serviceUrl:
      defaultZone: ${eureka-server.uri:http://127.0.0.1:8761}/eureka/
```
which tells this client where to find the Eureka Server Registry to register at. If there are multiple servers to register at, the above property can be provided with multiple comma separated uri's.

The `server.port` property for the "client" modules are set to 0 to facilitate starting up multiple instances. Being able to run multiple instances of a service opens up the gateway for setting up Load Balancing. To enable client side load balancing, the below dependency is added:
```bash
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-ribbon</artifactId>
		</dependency>
```
Netflix has a Eureka-aware client-side load-balancing client called [Ribbon](https://github.com/Netflix/ribbon) that Spring Cloud integrates extensively. Ribbon is a client library with built-in software load balancers.

To demonstrate service to service communication, in this case, between the `bus-service-client` and `train-service-client`, Spring Cloud Feign Integration is used. [Feign](https://github.com/Netflix/feign) is a handy project from Netflix that lets you describe a REST API client declaratively with annotations on an interface. To enable Feign, the below dependency is added:
```bash
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-feign</artifactId>
		</dependency>
```

and `@EnableFeignClients` annotation is added to the Spring configuration class above. An interface is created in `bus-service-client` module which is used to invoke the a service in `train-service-client`, like so:

```bash
@FeignClient("train-service-client")
public interface TrainFeignClient {

    @RequestMapping(value = "/train/{routeId}")
    String getRoutes(@PathVariable("routeId") Integer routeId);
}
```

This integration makes it simple to create smart, Eureka-aware REST clients that uses Ribbon for client-side load-balancing to pick an available service instance.


## Spring Netflix Zuul Routing
Spring Netflix Zuul Routing is used to provide a unified interface to the consumers of the system. If there are services that are split into small composable apps, this shouldn’t be visible to users or result in substantial development effort.

To solve this problem, Netflix created and open-sourced its Zuul proxy server. [Zuul](https://github.com/Netflix/zuul) is an edge service that proxies requests to multiple backing services. It provides a unified “front door” to the system, which allows a browser, mobile app, or other user interface to consume services from multiple hosts without managing cross-origin resource sharing (CORS) and authentication for each one.

To create a Zuul server, the below dependency is added:
```bash
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zuul</artifactId>
		</dependency>
``` 

The `@EnableZuulProxy` annotation is added to the Spring configuration class like so:
```bash
@SpringBootApplication
@EnableZuulProxy
@EnableAutoConfiguration
public class ZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApplication.class, args);
	}
}
```

Refer to the `src/main/resources/bootstrap.yml` file for various properties related to controlling the request timeouts.

## Spring Cloud Config Server
Spring Cloud Config is Spring’s client/server approach for storing and serving distributed configurations across multiple applications and environments.

The main part of the application is the Spring configuration class – more specifically a class with `@SpringBootApplication` annotation – which pulls in all the required setup through the auto-configure annotation `@EnableConfigServer`, like so:
```bash
@SpringBootApplication
@EnableConfigServer
public class ConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
	}
}
```

The below is added to the `src/main/resources/application.yml` file:
```bash
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/GreenMountainPower/spring-cloud-netflix-microservices
          search-paths: configs
```
This tells the location of the repository on github and the path for the configs inside the repository.

The below property is added to the `src/main/resources/bootstrap.yml` to specify that cloud config is enabled:
```bash
spring:
  cloud:
    config:
      enabled: true
```
The config files for each of the modules should have the same name as the `spring.application.name` property value and with .yml (or .properties) file extension. For multiple environments, the names can be appended with `-` and the environment name. For example, a test profile for `bus-service-client` module will have the config file name as `bus-service-client-test.yml` 

After uploading the config files to github, they can be accessed via Java based configuration. For example, the properties in `bus-service-client.yml` file can be accessed in Java by creating a Java pojo class like so:
```bash
@Component
@ConfigurationProperties
@RefreshScope
public class BusClientConfig {

    private Schedule schedule;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public static class Schedule {
        private List<String> routes;

        public List<String> getRoutes() {
            return routes;
        }

        public void setRoutes(List<String> routes) {
            this.routes = routes;
        }
    }
}
```

and autowiring the above class in any Spring of the managed classes (any class annotated with @Component or @Service or @Repository etc) like so:
```bash
 @Autowired private BusClientConfig busClientConfig;
 ```

The annotation `@RefreshScope` on a spring managed bean, enables properties to be [dynamically refreshed](http://cloud.spring.io/spring-cloud-static/docs/1.0.x/spring-cloud.html#_refresh_scope) by sending a `POST` request to `/refresh` endpoint. For example, after pushing the updates to `bus-service-client.yml` file, these updated can be made available by sendign a `POST` reqeust like so, with out having to restart any of the services:
```bash
curl -X "POST" "http://localhost:9000/api/bus-service/refresh"
```
