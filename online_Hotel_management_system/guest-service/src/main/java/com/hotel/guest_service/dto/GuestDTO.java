package com.hotel.guest_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class GuestDTO {

        @NotBlank(message = "Member code is required")
        private String memberCode;

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
        private String phoneNumber;

        private String company;

        @NotBlank(message = "Name is required")
        private String name;

        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Gender is required")
        private String gender;

        @NotBlank(message = "Address is required")
        private String address;
}
