package com.hotel.payment_service.repository;
import com.hotel.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    Optional<Payment> findByPaymentIntentId(String paymentIntentId);
    Optional<Payment> findByBookingId(Long bookingId);

}
