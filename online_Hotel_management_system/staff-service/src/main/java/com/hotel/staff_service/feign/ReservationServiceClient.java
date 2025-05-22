package com.hotel.staff_service.feign;

import com.hotel.staff_service.dto.ReservationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "reservation")
public interface ReservationServiceClient {

    @GetMapping("/api/reservations/staff/{staffId}")
    List<ReservationDTO> getReservationsByStaffId(@PathVariable("staffId") Long staffId);
}
