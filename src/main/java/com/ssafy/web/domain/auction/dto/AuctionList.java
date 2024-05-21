package com.ssafy.web.domain.auction.dto;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.entity.AuctionType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuctionList {
    private final Long auctionId;
    private final String location;
    private final Long startingBidPrice;
    private final AuctionType auctionType;
    private final LocalDateTime bidOpening;
    private final LocalDateTime bidClosing;

    public AuctionList(Long auctionId, String location, Long startingBidPrice, AuctionType auctionType,
                       LocalDateTime bidOpening, LocalDateTime bidClosing) {
        this.auctionId = auctionId;
        this.location = location;
        this.startingBidPrice = startingBidPrice;
        this.auctionType = auctionType;
        this.bidOpening = bidOpening;
        this.bidClosing = bidClosing;
    }

    public static AuctionList of(Auction auction) {
        return new AuctionList(auction.getAuctionId(), auction.getLocation(), auction.getStartingBidPrice(),
                auction.getAuctionType(), auction.getBidOpening(), auction.getBidClosing());
    }
}
