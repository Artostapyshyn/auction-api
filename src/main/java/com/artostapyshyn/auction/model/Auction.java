package com.artostapyshyn.auction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "auction")
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_price", nullable = false)
    private BigDecimal startPrice;

    @ElementCollection
    @CollectionTable(name = "auction_photos")
    @Column(name = "photos")
    private List<byte[]> photos;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Bid> bids;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}