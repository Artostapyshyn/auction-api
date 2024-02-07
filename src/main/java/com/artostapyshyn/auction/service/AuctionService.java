package com.artostapyshyn.auction.service;

import com.artostapyshyn.auction.dto.AuctionDto;
import com.artostapyshyn.auction.model.Auction;
import com.artostapyshyn.auction.model.Bid;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionService {
    Auction createAuction(AuctionDto auctionDto);

    List<Auction> findAll();

    Auction findById(Long id);

    Auction findByName(String name);

    Auction findByStartPrice(BigDecimal startPrice);

    void editAuction(AuctionDto auctionDto);

    List<Bid> getBidHistory(Long auctionId);
}
