package com.hotel.rate_service.config;

import com.hotel.rate_service.repository.RateRepository;
import com.hotel.rate_service.service.RateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateServiceConfig {
    private final RateRepository rateRepository;

    public RateServiceConfig(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Bean
    public RateService rateService() {
        return new RateService(rateRepository);
    }
}
