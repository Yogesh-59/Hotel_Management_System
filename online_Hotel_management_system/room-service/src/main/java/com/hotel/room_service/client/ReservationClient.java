package com.hotel.room_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservation")
public interface ReservationClient {
    @GetMapping("/reservations/room/{roomNumber}")
    boolean isRoomReserved(@PathVariable("roomNumber") int roomNumber);
}
