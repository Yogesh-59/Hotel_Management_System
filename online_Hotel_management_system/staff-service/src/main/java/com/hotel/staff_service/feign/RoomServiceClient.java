package com.hotel.staff_service.feign;

import com.hotel.staff_service.dto.RoomDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "room-service")
public interface RoomServiceClient {

    @GetMapping("/api/rooms/staff/{staffId}")
    List<RoomDTO> getRoomsManagedByStaff(@PathVariable("staffId") Long staffId);
}
