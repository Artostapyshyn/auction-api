package com.artostapyshyn.auction.dto;

import java.math.BigDecimal;

public record BidDto(Long auctionId, BigDecimal amount) {
}
