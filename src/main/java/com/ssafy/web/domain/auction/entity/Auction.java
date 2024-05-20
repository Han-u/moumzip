package com.ssafy.web.domain.auction.entity;

import java.time.LocalDateTime;

import com.ssafy.web.domain.member.entity.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long auctionId;
	private String location;
	private float supplyArea;
	private float exclusivePrivateArea;
	private long lowestBid;
	private long startingBidPrice;
	private long listingPrice;
	private long officialLandPrice;
	@Enumerated(EnumType.STRING)
	private Purpose purpose;
	@Enumerated(EnumType.STRING)
	private AuctionStatus status;
	private LocalDateTime bidOpening;
	private LocalDateTime bidClosing;
	private LocalDateTime bidClosingExtended;
	private long winningBidPrice;
	private String winningMemberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member winningBidder;

	@Builder
	public Auction(Long auctionId, String location, float supplyArea, float exclusivePrivateArea, long lowestBid,
                   long startingBidPrice, long listingPrice, long officialLandPrice, Purpose purpose, AuctionStatus status, LocalDateTime bidOpening,
                   LocalDateTime bidClosing, LocalDateTime bidClosingExtended, long winningBidPrice, String winningMemberId, Member winningBidder) {
		this.auctionId = auctionId;
		this.location = location;
		this.supplyArea = supplyArea;
		this.exclusivePrivateArea = exclusivePrivateArea;
		this.lowestBid = lowestBid;
		this.startingBidPrice = startingBidPrice;
		this.listingPrice = listingPrice;
        this.officialLandPrice = officialLandPrice;
        this.purpose = purpose;
		this.status = status;
		this.bidOpening = bidOpening;
		this.bidClosing = bidClosing;
        this.bidClosingExtended = bidClosingExtended;
        this.winningBidPrice = winningBidPrice;
        this.winningMemberId = winningMemberId;
        this.winningBidder = winningBidder;
	}

	public void update(Auction auction) {
		this.location = auction.location;
		this.supplyArea = auction.supplyArea;
		this.exclusivePrivateArea = auction.exclusivePrivateArea;
		this.lowestBid = auction.lowestBid;
		this.startingBidPrice = auction.startingBidPrice;
		this.listingPrice = auction.listingPrice;
		this.officialLandPrice = auction.officialLandPrice;
		this.purpose = auction.purpose;
		this.status = auction.status;
		this.bidOpening = auction.bidOpening;
		this.bidClosing = auction.bidClosing;
		this.bidClosingExtended = auction.bidClosingExtended;
		this.winningBidPrice = auction.winningBidPrice;
		this.winningMemberId = auction.winningMemberId;
		this.winningBidder = auction.winningBidder;
	}
}