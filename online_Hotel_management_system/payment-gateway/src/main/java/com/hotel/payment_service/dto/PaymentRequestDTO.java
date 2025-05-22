package com.hotel.payment_service.dto;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long bookingId;
    private String customerEmail;
    private Double amount;
    private String currency;
}
