package com.hotel.guest_service.config;

import com.hotel.guest_service.repository.GuestRepository;
import com.hotel.guest_service.service.GuestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GuestServiceConfig {
    @Bean
    public GuestService guestService(GuestRepository guestRepository) {
        return new GuestService(guestRepository);
    }
}
