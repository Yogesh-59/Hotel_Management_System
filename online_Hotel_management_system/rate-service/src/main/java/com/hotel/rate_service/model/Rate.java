package com.hotel.rate_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rate_service")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomType;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String season;

    private String description;
}
