package com.hotel.rate_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateRequestDTO {

    @NotBlank(message = "Room type is required")
    private String roomType;

    @Min(value = 0, message = "Price must be >= 0")
    private double price;

    @NotBlank(message = "Season is required")
    private String season;

    private String description;
}