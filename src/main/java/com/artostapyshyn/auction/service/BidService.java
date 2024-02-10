package com.artostapyshyn.auction.service;

import com.artostapyshyn.auction.dto.BidDto;
import com.artostapyshyn.auction.dto.PlacedBidDto;
import com.artostapyshyn.auction.model.Bid;
import com.artostapyshyn.auction.model.User;

import java.util.List;

public interface BidService {
    List<PlacedBidDto> findAllByAuctionId(Long auctionId);

    Bid save(Bid bid);

    PlacedBidDto getMaxBid(Long auctionId);

    void delete(Long id);

    PlacedBidDto findById(Long id);

    PlacedBidDto placeBid(User user, BidDto bidDto);
}
