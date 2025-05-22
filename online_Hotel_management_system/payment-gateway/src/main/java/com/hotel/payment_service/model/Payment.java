package com.hotel.payment_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;
    private String customerEmail;
    private Double amount;
    private String currency;
    private String paymentIntentId;
    private String status;
    private LocalDateTime createdAt;
}
