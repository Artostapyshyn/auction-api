package com.artostapyshyn.auction.repository;

import com.artostapyshyn.auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Optional<Auction> findById(Long id);

    Optional<Auction> findByName(String name);

    Optional<Auction> findByStartPrice(BigDecimal startPrice);
}