package com.gmp.bus;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusScheduleRepository extends MongoRepository<BusSchedule, String> {

    public BusSchedule findByStationName(String StationName);
    public List<BusSchedule> findByDepartureTime(String DepartureTime);
    public List<BusSchedule> findByBranchName(String BranchName);
}
