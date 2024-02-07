package com.artostapyshyn.auction.service;

import com.artostapyshyn.auction.dto.BidDto;
import com.artostapyshyn.auction.model.Bid;
import com.artostapyshyn.auction.model.User;

import java.util.List;

public interface BidService {
    List<Bid> findAllByAuctionId(Long auctionId);

    Bid save(Bid bid);

    void delete(Long id);

    Bid findById(Long id);

    Bid placeBid(User user, BidDto bidDto);
}
