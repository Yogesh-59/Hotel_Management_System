package com.hotel.rate_service.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateResponseDTO {
    private Long id;
    private String roomType;
    private double price;
    private String season;
    private String description;
}
