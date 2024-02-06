package com.artostapyshyn.auction.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpDto(@NotBlank(message = "First name is required")
                        String firstName,
                        @NotBlank(message = "Last name is required")
                        String lastName,
                        @Email(message = "Invalid email address")
                        String email,
                        @Size(min=8, message = "Password must be at least 8 characters long")
                        String password,
                        String city,
                        byte[] photoBytes){

}
