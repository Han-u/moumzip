package com.ssafy.web.domain.auction.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@SQLRestriction("auction_status = CANCELED")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long auctionId;

	@Version
	private Long version;
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
	@Enumerated(EnumType.STRING)
	private AuctionType auctionType;
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
	public Auction(Long auctionId, Long version, String location, Float supplyArea, Float exclusivePrivateArea, Long startingBidPrice,
		Long listingPrice, Long officialLandPrice, Purpose purpose, AuctionStatus auctionStatus,
		AuctionType auctionType,
		LocalDateTime bidOpening, LocalDateTime bidClosing, LocalDateTime bidClosingExtended, Long winningBidPrice,
		Member winningBidder) {
		this.auctionId = auctionId;
		this.version = version;
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

	public void update(Auction auction) {
		this.location = auction.location;
		this.supplyArea = auction.supplyArea;
		this.exclusivePrivateArea = auction.exclusivePrivateArea;
		this.startingBidPrice = auction.startingBidPrice;
		this.listingPrice = auction.listingPrice;
		this.officialLandPrice = auction.officialLandPrice;
		this.purpose = auction.purpose;
		this.auctionStatus = auction.auctionStatus;
		this.bidOpening = auction.bidOpening;
		this.bidClosing = auction.bidClosing;
		this.bidClosingExtended = auction.bidClosingExtended;
		this.winningBidPrice = auction.winningBidPrice;
		this.winningBidder = auction.winningBidder;
	}

	public void updateWinner(Long winningBidPrice, Member member) {
		if (winningBidPrice <= this.winningBidPrice) {
			throw new IllegalArgumentException("입력값이 최고가보다 낮습니다.");
		}
		this.winningBidPrice = winningBidPrice;
		this.winningBidder = member;
	}

	public boolean isInProgress() {
		return this.auctionStatus == AuctionStatus.PROGRESS &&
			this.bidOpening.isBefore(LocalDateTime.now()) &&
			this.bidClosingExtended.isAfter(LocalDateTime.now());
	}

	public boolean isWithinParticipationPeriod() {
		return this.getCreatedAt().toLocalDate().plusDays(5).isBefore(LocalDate.now())
			&& this.bidOpening.toLocalDate().isAfter(LocalDate.now());
	}

	public long getDepositPrice() {
		return (long)(this.startingBidPrice * 0.1);
	}

	public void updateClosingExtend(long minute) {
		if (bidClosingExtended.plusMinutes(minute).isAfter(bidClosing.plusHours(1))) {
			bidClosingExtended = bidClosing.plusHours(1);
		} else {
			bidClosingExtended = bidClosingExtended.plusMinutes(minute);
		}
	}
}
