package com.hotel.reservation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "room-service")
public interface RoomServiceClient {
    @GetMapping("/rooms/availability/{roomId}")
    Boolean isRoomAvailable(@PathVariable("roomId") Long roomId);
}
