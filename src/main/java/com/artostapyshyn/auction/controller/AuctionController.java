package com.artostapyshyn.auction.controller;

import com.artostapyshyn.auction.dto.AuctionDto;
import com.artostapyshyn.auction.dto.BidDto;
import com.artostapyshyn.auction.model.Auction;
import com.artostapyshyn.auction.model.Bid;
import com.artostapyshyn.auction.service.AuctionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/auctions")
@CrossOrigin(maxAge = 3600, origins = "*")
@AllArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Create auction")
    @PostMapping("/create")
    public ResponseEntity<Auction> createAuction(@NotNull @RequestBody AuctionDto auctionDto) {
        Auction createdAuction = auctionService.createAuction(auctionDto);
        return new ResponseEntity<>(createdAuction, HttpStatus.OK);
    }

    @Operation(summary = "Get all auctions")
    @GetMapping("/all")
    public ResponseEntity<List<AuctionDto>> getAllAuctions() {
        List<AuctionDto> auctions = auctionService.findAll();
        return new ResponseEntity<>(auctions, HttpStatus.OK);
    }

    @Operation(summary = "Get auction by id")
    @GetMapping("/get-by-id")
    public ResponseEntity<AuctionDto> getAuctionById(@NotNull @RequestParam Long id) {
        AuctionDto auction = auctionService.findById(id);
        return new ResponseEntity<>(auction, HttpStatus.OK);
    }

    @Operation(summary = "Get auction by name")
    @GetMapping("/name")
    public ResponseEntity<AuctionDto> getAuctionByName(@NotNull @RequestParam String name) {
        AuctionDto auction = auctionService.findByName(name);
        return new ResponseEntity<>(auction, HttpStatus.OK);
    }

    @Operation(summary = "Get auction by start price")
    @GetMapping("/start-price")
    public ResponseEntity<AuctionDto> getAuctionByStartPrice(@NotNull @RequestParam BigDecimal startPrice) {
        AuctionDto auction = auctionService.findByStartPrice(startPrice);
        return new ResponseEntity<>(auction, HttpStatus.OK);
    }

    @Operation(summary = "Get bids history")
    @GetMapping("/bids")
    public ResponseEntity<List<BidDto>> getBidHistory(@RequestParam("auctionId") Long auctionId) {
        List<BidDto> bidHistory = auctionService.getBidHistory(auctionId);
        return new ResponseEntity<>(bidHistory, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Edit auction")
    @PutMapping("/edit")
    public ResponseEntity<Auction> editAuction(@NotNull @RequestBody AuctionDto auctionDto) {
        auctionService.editAuction(auctionDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
