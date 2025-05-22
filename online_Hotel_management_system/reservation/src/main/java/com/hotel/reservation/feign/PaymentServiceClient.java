package com.hotel.reservation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-gateway")
public interface PaymentServiceClient {
    @GetMapping("/api/payments/status/{bookingId}")
    String getPaymentStatusByBookingId(@PathVariable("bookingId") Long bookingId);
}