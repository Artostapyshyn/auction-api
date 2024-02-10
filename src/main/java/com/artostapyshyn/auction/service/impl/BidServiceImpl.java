package com.artostapyshyn.auction.service.impl;

import com.artostapyshyn.auction.dto.BidDto;
import com.artostapyshyn.auction.dto.PlacedBidDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final UserService userService;
    private final AuctionRepository auctionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PlacedBidDto> findAllByAuctionId(Long auctionId) {
        List<Bid> bids = bidRepository.findAllByAuctionId(auctionId);
        return bids.stream()
                .map(bid -> modelMapper.map(bid, PlacedBidDto.class))
                .toList();
    }

    @Override
    public Bid save(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public PlacedBidDto getMaxBid(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(AuctionNotFoundException::new);

        return auction.getBids().stream()
                .max(Comparator.comparing(Bid::getPrice))
                .map(bid -> modelMapper.map(bid, PlacedBidDto.class))
                .orElseThrow(BidNotFoundException::new);
    }


    @Override
    public void delete(Long id) {
        Bid bid = bidRepository.findById(id).orElseThrow(BidNotFoundException::new);
        if (userService.getAuthenticatedPerson() == bid.getUser()) {
            bidRepository.deleteById(id);
        }
    }

    @Override
    public PlacedBidDto findById(Long id) {
        Bid bid = bidRepository.findById(id).orElseThrow(BidNotFoundException::new);
        return modelMapper.map(bid, PlacedBidDto.class);
    }

    @Override
    public PlacedBidDto placeBid(User user, BidDto bidDto) {
        Auction auction = auctionRepository.findById(bidDto.getAuctionId()).orElseThrow(AuctionNotFoundException::new);
        BigDecimal bidAmount = bidDto.getAmount();
        if (auction.getEndDate().isAfter(LocalDateTime.now())) {
            if (auction.getBids().isEmpty() || bidAmount.compareTo(getCurrentHighestBid(auction)) > 0) {
                Bid bid = new Bid();
                bid.setUser(user);
                bid.setAuction(auction);
                bid.setPrice(bidAmount);

                bidRepository.save(bid);

                auction.getBids().add(bid);
                auctionRepository.save(auction);
                return modelMapper.map(bid, PlacedBidDto.class);
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
