package com.ssafy.web.domain.auction.dto;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.auction.entity.AuctionType;
import com.ssafy.web.domain.auction.entity.Purpose;
import com.ssafy.web.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionDetail {
    private Long auctionId;
    private String location;
    private Float supplyArea;
    private Float exclusivePrivateArea;
    private Long startingBidPrice;
    private Long listingPrice;
    private Long officialLandPrice;
    private Purpose purpose;
    private AuctionStatus auctionStatus;
    private AuctionType auctionType;
    private LocalDateTime bidOpening;
    private LocalDateTime bidClosing;
    private LocalDateTime bidClosingExtended;
    private Long winningBidPrice;
    private Member winningBidder;

    // 생성자, 빌더 등 필요한 메서드 추가 가능

    @Builder
    public AuctionDetail(Long auctionId, String location, Float supplyArea, Float exclusivePrivateArea,
                         Long startingBidPrice, Long listingPrice, Long officialLandPrice, Purpose purpose,
                         AuctionStatus auctionStatus, AuctionType auctionType, LocalDateTime bidOpening,
                         LocalDateTime bidClosing, LocalDateTime bidClosingExtended, Long winningBidPrice,
                         Member winningBidder) {
        this.auctionId = auctionId;
        this.location = location;
        this.supplyArea = supplyArea;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.startingBidPrice = startingBidPrice;
        this.listingPrice = listingPrice;
        this.officialLandPrice = officialLandPrice;
        this.purpose = purpose;
        this.auctionStatus = auctionStatus;
        this.auctionType = auctionType;
        this.bidOpening = bidOpening;
        this.bidClosing = bidClosing;
        this.bidClosingExtended = bidClosingExtended;
        this.winningBidPrice = winningBidPrice;
        this.winningBidder = winningBidder;
    }

    public static AuctionDetail of(Auction auction) {
        return AuctionDetail.builder()
                .auctionId(auction.getAuctionId())
                .location(auction.getLocation())
                .supplyArea(auction.getSupplyArea())
                .exclusivePrivateArea(auction.getExclusivePrivateArea())
                .startingBidPrice(auction.getStartingBidPrice())
                .listingPrice(auction.getListingPrice())
                .officialLandPrice(auction.getOfficialLandPrice())
                .purpose(auction.getPurpose())
                .auctionStatus(auction.getAuctionStatus())
                .auctionType(auction.getAuctionType())
                .bidOpening(auction.getBidOpening())
                .bidClosing(auction.getBidClosing())
                .bidClosingExtended(auction.getBidClosingExtended())
                .winningBidPrice(auction.getWinningBidPrice())
                .winningBidder(auction.getWinningBidder())
                .build();
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
                .auctionStatus(auctionStatus)
                .auctionType(auctionType)
                .bidOpening(bidOpening)
                .bidClosing(bidClosing)
                .bidClosingExtended(bidClosingExtended)
                .winningBidPrice(winningBidPrice)
                .winningBidder(winningBidder)
                .build();
    }
}

