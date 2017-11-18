package com.gmp.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class BusClientApplication {

	@Autowired
	private BusScheduleRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BusClientApplication.class, args);
	}

	// @Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		//gladstone branch
		repository.save(new BusSchedule("Newark", "06:00", "Gladstone"));
		repository.save(new BusSchedule("Newark", "06:00", "Gladstone"));
		repository.save(new BusSchedule("East Orange", "8:00", "Gladstone"));
		repository.save(new BusSchedule("Brick Church", "9:00", "Gladstone"));
		repository.save(new BusSchedule("Moutain", "10:30", "Gladstone"));
		repository.save(new BusSchedule("Maplewood", "10:50", "Gladstone"));
		repository.save(new BusSchedule("Short Hills", "11:20", "Gladstone"));
		repository.save(new BusSchedule("Gladstone", "13:45", "Gladstone"));
	}
}
