package com.ssafy.web.domain.auction.dto;

import java.time.LocalDateTime;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.auction.entity.AuctionType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionDto {
	private String location;
	private AuctionType auctionType;
	private AuctionStatus auctionStatus;
	private LocalDateTime bidOpening;
	private LocalDateTime bidClosing;
	private LocalDateTime bidClosingExtended;

	@Builder
	public AuctionDto(String location, AuctionType auctionType, AuctionStatus auctionStatus, LocalDateTime bidOpening,
		LocalDateTime bidClosing, LocalDateTime bidClosingExtended) {
		this.location = location;
		this.auctionType = auctionType;
		this.auctionStatus = auctionStatus;
		this.bidOpening = bidOpening;
		this.bidClosing = bidClosing;
		this.bidClosingExtended = bidClosingExtended;
	}

	public static AuctionDto of(Auction auction){
		return AuctionDto.builder()
			.location(auction.getLocation())
			.auctionType(auction.getAuctionType())
			.auctionStatus(auction.getAuctionStatus())
			.bidOpening(auction.getBidOpening())
			.bidClosing(auction.getBidClosing())
			.bidClosingExtended(auction.getBidClosingExtended())
			.build();
	}
}
