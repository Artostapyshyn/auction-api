package com.artostapyshyn.auction.service.impl;

import com.artostapyshyn.auction.dto.AuctionDto;
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

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Override
    public Auction createAuction(AuctionDto auctionDto) {
        Auction auction = modelMapper.map(auctionDto, Auction.class);
        auction.setOwner(userService.getAuthenticatedPerson());
        return auctionRepository.save(auction);
    }

    @Override
    public List<Auction> findAll() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction findById(Long id) {
        return auctionRepository.findById(id).orElseThrow(AuctionNotFoundException::new);
    }

    @Override
    public Auction findByName(String name) {
        return auctionRepository.findByName(name).orElseThrow(AuctionNotFoundException::new);
    }

    @Override
    public Auction findByStartPrice(BigDecimal startPrice) {
        return auctionRepository.findByStartPrice(startPrice).orElseThrow(AuctionNotFoundException::new);
    }

    @Override
    public void editAuction(AuctionDto auctionDto) {

        Auction existingAuction = auctionRepository.findById(auctionDto.id())
                .orElseThrow(AuctionNotFoundException::new);

        if(existingAuction.getOwner() == userService.getAuthenticatedPerson()) {
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(auctionDto, existingAuction);
        }
        auctionRepository.save(existingAuction);
    }

    @Override
    public List<Bid> getBidHistory(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(AuctionNotFoundException::new);

        return auction.getBids();
    }

}
