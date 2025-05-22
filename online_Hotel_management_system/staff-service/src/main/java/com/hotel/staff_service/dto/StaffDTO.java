package com.hotel.staff_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private String code;
    private String employeeName;
    private String employeeAddress;
    private String nic;
    private double salary;
    private int age;
    private String occupation;
    private String email;
}
