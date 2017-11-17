package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private BusScheduleRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		//gladstone branch
		repository.save(new BusSchedule("Newark", "06:00", "Gladstone"));
		repository.save(new BusSchedule("East Orange", "8:00", "Gladstone"));
		repository.save(new BusSchedule("Brick Church", "9:00", "Gladstone"));
		repository.save(new BusSchedule("Moutain", "10:30", "Gladstone"));
		repository.save(new BusSchedule("Maplewood", "10:50", "Gladstone"));
		repository.save(new BusSchedule("Short Hills", "11:20", "Gladstone"));
		repository.save(new BusSchedule("Gladstone", "13:45", "Gladstone"));

		//northeast branch
		repository.save(new BusSchedule("New York", "06:00", "Northeast"));
		repository.save(new BusSchedule("Hartford", "8:00", "Northeast"));
		repository.save(new BusSchedule("New Haven", "9:00", "Northeast"));
		repository.save(new BusSchedule("Allston", "10:30", "Northeast"));
		repository.save(new BusSchedule("Newton", "10:50", "Northeast"));
		repository.save(new BusSchedule("Boston", "11:20", "Northeast"));
		repository.save(new BusSchedule("Concord", "13:45", "Northeast"));

		//jersey shore branch
		repository.save(new BusSchedule("Newark", "06:00", "Jersey Shore"));
		repository.save(new BusSchedule("Linden", "06:30", "Jersey Shore"));
		repository.save(new BusSchedule("Avenel", "08:00", "Jersey Shore"));
		repository.save(new BusSchedule("Woodbridge", "09:00", "Jersey Shore"));
		repository.save(new BusSchedule("Red Bank", "10:30", "Jersey Shore"));
		repository.save(new BusSchedule("Belmar", "10:50", "Jersey Shore"));
		repository.save(new BusSchedule("Bay Head", "13:45", "Jersey Shore"));

		// fetch all bus schedules
		System.out.println("busSchedule found with findAll():");
		System.out.println("-------------------------------");
		for (BusSchedule busSchedule : repository.findAll()) {
			System.out.println(busSchedule);
		}
		System.out.println();

		//fetch an individual bus schedule
		System.out.println("busSchedule found with findByStationName('Boston'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByStationName("Boston"));

		System.out.println("busSchedules found with findByDepartureTime('06:00'):");
		System.out.println("--------------------------------");
		for (BusSchedule busSchedule : repository.findByDepartureTime("06:00")) {
			System.out.println(busSchedule);
		}

		System.out.println("busSchedules found with findByDepartureTime('06:30'):");
		System.out.println("--------------------------------");
		for (BusSchedule busSchedule : repository.findByDepartureTime("06:30")) {
			System.out.println(busSchedule);
		}

		System.out.println("busSchedules found with findByBranchName('Gladstone'):");
		System.out.println("--------------------------------");
		for (BusSchedule busSchedule : repository.findByBranchName("Gladstone")) {
			System.out.println(busSchedule);
		}

		System.out.println("busSchedules found with findByBranchName('Northeast'):");
		System.out.println("--------------------------------");
		for (BusSchedule busSchedule : repository.findByBranchName("Northeast")) {
			System.out.println(busSchedule);
		}

		System.out.println("busSchedules found with findByBranchName('Jersey Shore'):");
		System.out.println("--------------------------------");
		for (BusSchedule busSchedule : repository.findByBranchName("Jersey Shore")) {
			System.out.println(busSchedule);
		}
	}

}