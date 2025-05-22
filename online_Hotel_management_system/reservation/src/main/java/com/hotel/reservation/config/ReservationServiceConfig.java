package com.hotel.reservation.config;

import com.hotel.reservation.feign.GuestServiceClient;
import com.hotel.reservation.feign.PaymentServiceClient;
import com.hotel.reservation.feign.RoomServiceClient;
import com.hotel.reservation.repository.ReservationRepository;
import com.hotel.reservation.service.ReservationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationServiceConfig {

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository,
                                                 RoomServiceClient roomServiceClient,
                                                 GuestServiceClient guestServiceClient,
                                                 PaymentServiceClient paymentServiceClient) {
        return new ReservationService(reservationRepository, roomServiceClient, guestServiceClient, paymentServiceClient);
    }
}
