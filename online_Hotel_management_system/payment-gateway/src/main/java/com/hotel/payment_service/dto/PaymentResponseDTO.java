package com.hotel.payment_service.dto;
import lombok.Data;

@Data
public class PaymentResponseDTO {
    private Long paymentId;
    private String paymentIntentId;
    private String clientSecret;
    private String status;
}
