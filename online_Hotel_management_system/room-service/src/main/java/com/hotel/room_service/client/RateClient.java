package com.hotel.room_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rate-service")
public interface RateClient {
    @GetMapping("/rates/getRateByRoomType")
    Double getRateByRoomType(@RequestParam("roomType") String roomType);;
}
