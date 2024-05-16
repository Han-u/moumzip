package com.ssafy.web.domain.auction.entity;

import java.time.LocalDateTime;

import com.ssafy.web.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private long startingBid;
	private long listingPrice;
	@Enumerated(EnumType.STRING)
	private Usage usage;
	@Enumerated(EnumType.STRING)
	private AuctionStatus status;
	private LocalDateTime bidOpening;
	private LocalDateTime bidClosing;
	private long winningBid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member winningBidder;

	@Builder
	public Auction(Long auctionId, String location, float supplyArea, float exclusivePrivateArea, long lowestBid,
		long startingBid, long listingPrice, Usage usage, AuctionStatus status, LocalDateTime bidOpening,
		LocalDateTime bidClosing, long winningBid, Member winningBidder) {
		this.auctionId = auctionId;
		this.location = location;
		this.supplyArea = supplyArea;
		this.exclusivePrivateArea = exclusivePrivateArea;
		this.lowestBid = lowestBid;
		this.startingBid = startingBid;
		this.listingPrice = listingPrice;
		this.usage = usage;
		this.status = status;
		this.bidOpening = bidOpening;
		this.bidClosing = bidClosing;
		this.winningBid = winningBid;
		this.winningBidder = winningBidder;
	}
}
