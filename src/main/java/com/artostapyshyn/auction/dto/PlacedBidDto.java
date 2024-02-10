package com.artostapyshyn.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlacedBidDto {
    private Long auctionId;
    private BigDecimal price;
    private LocalDateTime date;
    private UserDto user;
}
