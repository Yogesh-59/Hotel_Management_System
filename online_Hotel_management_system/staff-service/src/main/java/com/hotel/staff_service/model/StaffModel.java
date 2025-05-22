package com.hotel.staff_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "staff_table")
public class StaffModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String employeeName;
    private String employeeAddress;
    private String nic;
    private double salary;
    private int age;
    private String occupation;
    private String email;
}
