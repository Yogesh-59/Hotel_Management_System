package com.hotel.staff_service.dto;

import lombok.Data;

@Data
public class ReservationDTO {
    private Long id;
    private String code;
    private String status;
    private int numNights;
}
