package com.hotel.guest_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guests_table")
public class GuestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Member code is required")
    @Column(unique = true, nullable = false)
    private String memberCode;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(nullable = false)
    private String phoneNumber;

    private String company;

    @NotNull(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Gender is required")
    @Column(nullable = false)
    private String gender;

    @NotNull(message = "Address is required")
    @Column(nullable = false)
    private String address;
}
