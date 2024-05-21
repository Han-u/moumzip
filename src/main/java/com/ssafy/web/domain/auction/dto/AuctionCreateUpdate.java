package com.ssafy.web.domain.auction.dto;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.entity.AuctionType;
import com.ssafy.web.domain.auction.entity.Purpose;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionCreateUpdate {
    private String location;
    private Float supplyArea;
    private Float exclusivePrivateArea;
    private Long startingBidPrice;
    private Long listingPrice;
    private Long officialLandPrice;
    private Purpose purpose;
    private AuctionType auctionType;
    private LocalDateTime bidOpening;
    private LocalDateTime bidClosing;

    @Builder
    public AuctionCreateUpdate(String location, Float supplyArea, Float exclusivePrivateArea, Long startingBidPrice,
                               Long listingPrice, Long officialLandPrice, Purpose purpose, AuctionType auctionType,
                               LocalDateTime bidOpening, LocalDateTime bidClosing) {
        this.location = location;
        this.supplyArea = supplyArea;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.startingBidPrice = startingBidPrice;
        this.listingPrice = listingPrice;
        this.officialLandPrice = officialLandPrice;
        this.purpose = purpose;
        this.auctionType = auctionType;
        this.bidOpening = bidOpening;
        this.bidClosing = bidClosing;
    }

    public Auction toEntity() {
        return Auction.builder()
                .location(location)
                .supplyArea(supplyArea)
                .exclusivePrivateArea(exclusivePrivateArea)
                .startingBidPrice(startingBidPrice)
                .listingPrice(listingPrice)
                .officialLandPrice(officialLandPrice)
                .purpose(purpose)
                .auctionType(auctionType)
                .bidOpening(bidOpening)
                .bidClosing(bidClosing)
                .build();
    }
}
