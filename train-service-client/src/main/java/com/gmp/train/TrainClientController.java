package com.gmp.train;

import com.gmp.train.config.TrainClientConfig;
import com.gmp.train.feign.BusFeignClient;
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
@RequestMapping("/train")
public class TrainClientController {

    private static final Logger LOG = LoggerFactory.getLogger(TrainClientController.class);

    @Autowired private TrainClientConfig trainClientConfig;
    @Autowired private BusFeignClient busFeignClient;


    @RequestMapping(method = RequestMethod.GET)
    public List<String> getAll() {
        LOG.info("... returning all train station routes.");
        return trainClientConfig.getSchedule().getRoutes();
    }

    @RequestMapping(value = "/bus/{routeId}", method = RequestMethod.GET)
    public String getBusFeign(@PathVariable Integer routeId) {
        LOG.info("... invoking bus feign/intra service with routeId -> {}", routeId);
        return busFeignClient.getRoute(routeId);
    }

    @RequestMapping(value = "/{routeId}", method = RequestMethod.GET)
    public String get(@PathVariable Integer routeId) {
        LOG.info(" ... service invoked with routeId -> {}",  routeId);
        List<String> routes = trainClientConfig.getSchedule().getRoutes();
        if (routeId < 1 ||routeId > 3) {
            int index = ThreadLocalRandom.current().nextInt(1, 4);
            return routes.get(index-1);
        }
        return routes.get(routeId-1);

    }
}
