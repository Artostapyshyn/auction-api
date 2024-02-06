package com.artostapyshyn.auction.dto;

import jakarta.validation.constraints.Email;

public record LoginDto(@Email(message = "Invalid email address")
                       String email,
                       String password) {
}
