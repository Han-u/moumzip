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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
	private Float supplyArea;
	private Float exclusivePrivateArea;
	@NotNull
	private Long startingBidPrice;
	private Long listingPrice;
	private Long officialLandPrice;
	@Enumerated(EnumType.STRING)
	private Purpose purpose;
	@Enumerated(EnumType.STRING)
	private AuctionStatus auctionStatus;
	@NotNull
	private LocalDateTime bidOpening;
	@NotNull
	private LocalDateTime bidClosing;
	private LocalDateTime bidClosingExtended;
	private Long winningBidPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "winning_member_id")
	private Member winningBidder;

	@Builder
	public Auction(Long auctionId, String location, Float supplyArea, Float exclusivePrivateArea,
		Long startingBidPrice, Long listingPrice, Long officialLandPrice, Purpose purpose, AuctionStatus auctionStatus, LocalDateTime bidOpening,
		LocalDateTime bidClosing, LocalDateTime bidClosingExtended, Long winningBidPrice, Member winningBidder) {
		this.auctionId = auctionId;
		this.location = location;
		this.supplyArea = supplyArea;
		this.exclusivePrivateArea = exclusivePrivateArea;
		this.startingBidPrice = startingBidPrice;
		this.officialLandPrice = officialLandPrice;
		this.listingPrice = listingPrice;
		this.purpose = purpose;
		this.auctionStatus = auctionStatus;
		this.bidOpening = bidOpening;
		this.bidClosing = bidClosing;
		this.bidClosingExtended = bidClosingExtended;
		this.winningBidPrice = winningBidPrice;
		this.winningBidder = winningBidder;
	}
}
