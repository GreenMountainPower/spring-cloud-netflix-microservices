package com.gmp.train.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("bus-service-client")
public interface BusFeignClient {

    @RequestMapping(value = "/bus/{routeId}")
    String getRoute(@PathVariable("routeId") Integer routeId);
}
