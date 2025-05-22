package com.hotel.reservation.feign;

import com.hotel.reservation.dto.GuestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "guest-service")
public interface GuestServiceClient {
    @GetMapping("/guest/get/id/{id}")
    GuestDTO getGuestById(@PathVariable("id") Long guestId);
}
