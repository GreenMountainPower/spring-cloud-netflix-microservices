package com.gmp.bus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("train-service-client")
public interface TrainFeignClient {

    @RequestMapping(value = "/train/{routeId}")
    String getRoutes(@PathVariable("routeId") Integer routeId);
}
