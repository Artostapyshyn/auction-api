package com.artostapyshyn.auction.service.impl;

import com.artostapyshyn.auction.dto.BidDto;
import com.artostapyshyn.auction.exception.AuctionClosedException;
import com.artostapyshyn.auction.exception.AuctionNotFoundException;
import com.artostapyshyn.auction.exception.BidNotFoundException;
import com.artostapyshyn.auction.exception.InvalidBidException;
import com.artostapyshyn.auction.model.Auction;
import com.artostapyshyn.auction.model.Bid;
import com.artostapyshyn.auction.model.User;
import com.artostapyshyn.auction.repository.AuctionRepository;
import com.artostapyshyn.auction.repository.BidRepository;
import com.artostapyshyn.auction.service.BidService;
import com.artostapyshyn.auction.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final UserService userService;
    private final AuctionRepository auctionRepository;

    @Override
    public List<Bid> findAllByAuctionId(Long auctionId) {
        return bidRepository.findAllByAuctionId(auctionId);
    }

    @Override
    public Bid save(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public void delete(Long id) {
        Bid bid = findById(id);
        if(userService.getAuthenticatedPerson() == bid.getUser()) {
            bidRepository.deleteById(id);
        }
    }

    @Override
    public Bid findById(Long id) {
        return bidRepository.findById(id).orElseThrow(BidNotFoundException::new);
    }

    @Override
    public Bid placeBid(User user, BidDto bidDto) {
        Auction auction = auctionRepository.findById(bidDto.auctionId()).orElseThrow(AuctionNotFoundException::new);
        BigDecimal bidAmount = bidDto.amount();
        if (auction.getEndDate().isAfter(LocalDateTime.now())) {
            if (auction.getBids().isEmpty() || bidAmount.compareTo(getCurrentHighestBid(auction)) > 0) {
                Bid bid = new Bid();
                bid.setUser(user);
                bid.setAuction(auction);
                bid.setPrice(bidAmount);

                auction.getBids().add(bid);
                auctionRepository.save(auction);
                return bid;
            } else {
                throw new InvalidBidException("Bid amount must be greater than the current highest bid");
            }
        } else {
            throw new AuctionClosedException("Cannot place bid on a closed auction");
        }
    }

    private BigDecimal getCurrentHighestBid(Auction auction) {
        return auction.getBids().stream()
                .map(Bid::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(auction.getStartPrice());
    }
}
