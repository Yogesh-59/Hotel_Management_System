package com.hotel.rate_service.repository;

import com.hotel.rate_service.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByRoomType(String roomType);
}
