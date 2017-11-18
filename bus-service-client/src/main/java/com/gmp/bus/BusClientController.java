package com.gmp.bus;

import com.gmp.bus.config.BusClientConfig;
import com.gmp.bus.feign.TrainFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/bus")
public class BusClientController {
    private static final Logger LOG = LoggerFactory.getLogger(BusClientController.class);

    @Autowired private BusClientConfig busClientConfig;
    @Autowired private TrainFeignClient trainFeignClient;
	@Autowired
	private BusScheduleRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<String> getAll() {
        //trying to return JSON from repository here
        //e.g. return repository.findAll();

        return busClientConfig.getSchedule().getRoutes();
    }

    @RequestMapping(value = "/{routeId}", method = RequestMethod.GET)
    public String get(@PathVariable Integer routeId) {
        LOG.info("... service invoked with routeId -> {}", routeId);
        List<String> routes = busClientConfig.getSchedule().getRoutes();
        if (routeId < 1 ||routeId > 3) {
            int index = ThreadLocalRandom.current().nextInt(1, 4);
            return routes.get(index-1);
        }
        return routes.get(routeId-1);
    }

    @RequestMapping(value = "/train/{routeId}", method = RequestMethod.GET)
    public String trainRoutes(@PathVariable Integer routeId) {
        LOG.info("... invoking via feign/intra service with routeId -> {}", routeId);
        return trainFeignClient.getRoutes(routeId);
    }
}
