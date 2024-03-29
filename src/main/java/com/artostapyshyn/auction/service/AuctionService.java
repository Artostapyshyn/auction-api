package com.artostapyshyn.auction.service;

import com.artostapyshyn.auction.dto.AuctionDto;
import com.artostapyshyn.auction.dto.BidDto;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionService {
    AuctionDto createAuction(AuctionDto auctionDto);

    List<AuctionDto> findAll();

    AuctionDto findById(Long id);

    AuctionDto findByName(String name);

    AuctionDto findByStartPrice(BigDecimal startPrice);

    void editAuction(AuctionDto auctionDto);

    List<BidDto> getBidHistory(Long auctionId);
}
