package hello;

import org.springframework.data.annotation.Id;

public class BusSchedule {

    @Id
    public String id;

    public String stationName;
    public String departureTime;

    public BusSchedule() {}

    public BusSchedule(String stationName, String departureTime) {
        this.stationName = stationName;
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return String.format(
                "BusSchedule[id=%s, stationName='%s', departureTime='%s']",
                id, stationName, departureTime);
    }

}

