package com.gmp.bus;

import org.springframework.data.annotation.Id;

public class BusSchedule {

    @Id
    public String id;

    public String stationName;
    public String departureTime;
    public String branchName;

    public BusSchedule() {}

    public BusSchedule(String stationName, String departureTime, String branchName) {
        this.stationName = stationName;
        this.departureTime = departureTime;
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return String.format(
                "BusSchedule[id=%s, stationName='%s', departureTime='%s', branchName='%s']",
                id, stationName, departureTime, branchName);
    }

}

