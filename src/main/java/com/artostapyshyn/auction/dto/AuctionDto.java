package com.artostapyshyn.auction.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record AuctionDto(Long id,
                         @NotBlank(message = "Name is required")
                         String name,
                         @NotBlank(message = "Description is required")
                         String description,
                         @NotBlank(message = "Start price is required")
                         String startPrice,
                         List<String> photos,
                         @NotBlank(message = "End date is required")
                         LocalDateTime endDate){
}
