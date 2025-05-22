package com.hotel.payment_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Replace "rate-service" with the Eureka service name of your rate microservice
@FeignClient(name = "rate-service")
public interface RateServiceClient {

    @GetMapping("/rates/getRateByRoomType")
    Double getRateByRoomType(@RequestParam("roomType") String roomType);
}
