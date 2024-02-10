package com.artostapyshyn.auction.controller;

import com.artostapyshyn.auction.dto.BidDto;
import com.artostapyshyn.auction.dto.PlacedBidDto;
import com.artostapyshyn.auction.service.BidService;
import com.artostapyshyn.auction.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/bids")
@CrossOrigin(maxAge = 3600, origins = "*")
@AllArgsConstructor
public class BidController {

    private final BidService bidService;
    private final UserService userService;

    @Operation(summary = "Get all bids")
    @GetMapping
    public ResponseEntity<List<PlacedBidDto>> getAllBids(@RequestParam("auctionId") Long auctionId) {
        return ResponseEntity.ok(bidService.findAllByAuctionId(auctionId));
    }

    @Operation(summary = "Place bid")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/place")
    public ResponseEntity<PlacedBidDto> placeBid(@RequestBody BidDto bidDto) {
        return ResponseEntity.ok(bidService.placeBid(userService.getAuthenticatedPerson(), bidDto));
    }

    @Operation(summary = "Delete bid")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping
    public ResponseEntity<Void> deleteBid(@RequestParam("bidId") Long bidId) {
        bidService.delete(bidId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get max bid")
    @GetMapping("/max")
    public ResponseEntity<PlacedBidDto> getMaxBid(@RequestParam("auctionId") Long auctionId) {
        return ResponseEntity.ok(bidService.getMaxBid(auctionId));
    }
}
