package com.hotel.room_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private int roomNumber;
    private boolean isAvailable;
    private boolean isBooked;
    private double price;
}
