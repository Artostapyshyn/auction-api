package com.artostapyshyn.auction.service.impl;

import com.artostapyshyn.auction.dto.AuctionDto;
import com.artostapyshyn.auction.dto.BidDto;
import com.artostapyshyn.auction.exception.AuctionNotFoundException;
import com.artostapyshyn.auction.model.Auction;
import com.artostapyshyn.auction.model.Bid;
import com.artostapyshyn.auction.repository.AuctionRepository;
import com.artostapyshyn.auction.service.AuctionService;
import com.artostapyshyn.auction.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public AuctionDto createAuction(AuctionDto auctionDto) {
        Auction auction = modelMapper.map(auctionDto, Auction.class);
        auction.setOwner(userService.getAuthenticatedPerson());
        auctionRepository.save(auction);
        return auctionDto;
    }

    @Override
    public List<AuctionDto> findAll() {
        List<Auction> auctions = auctionRepository.findAll();
        return auctions.stream()
                .map(auction -> modelMapper.map(auction, AuctionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AuctionDto findById(Long id) {
        Auction auction = auctionRepository.findById(id).orElseThrow(AuctionNotFoundException::new);
        return modelMapper.map(auction, AuctionDto.class);
    }

    @Override
    public AuctionDto findByName(String name) {
        Auction auction = auctionRepository.findByName(name).orElseThrow(AuctionNotFoundException::new);
        return modelMapper.map(auction, AuctionDto.class);
    }

    @Override
    public AuctionDto findByStartPrice(BigDecimal startPrice) {
        Auction auction = auctionRepository.findByStartPrice(startPrice).orElseThrow(AuctionNotFoundException::new);
        return modelMapper.map(auction, AuctionDto.class);
    }

    @Override
    public void editAuction(AuctionDto auctionDto) {
        Auction existingAuction = auctionRepository.findById(auctionDto.getId())
                .orElseThrow(AuctionNotFoundException::new);

        if(existingAuction.getOwner() == userService.getAuthenticatedPerson()) {
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(auctionDto, existingAuction);
            auctionRepository.save(existingAuction);
        }
    }

    @Override
    public List<BidDto> getBidHistory(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(AuctionNotFoundException::new);

        List<Bid> bids = auction.getBids();
        return bids.stream()
                .map(bid -> modelMapper.map(bid, BidDto.class))
                .collect(Collectors.toList());
    }
}
