package com.artostapyshyn.auction.repository;

import com.artostapyshyn.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByAuctionId(Long auctionId);
    Optional<Bid> findById(Long id);
}