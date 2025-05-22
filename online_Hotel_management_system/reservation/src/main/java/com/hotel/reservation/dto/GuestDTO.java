package com.hotel.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {
    private String memberCode;
    private String phoneNumber;
    private String company;
    private String name;
    private String email;
    private String gender;
    private String address;
}
