package com.ssafy.web.domain.deposit.dto;

import com.ssafy.web.domain.auction.dto.AuctionDto;
import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.entity.DepositStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositDto {
	private Long amount;
	private AuctionDto auctionDto;
	private DepositStatus depositStatus;
	private Long bidPrice;

	@Builder
	public DepositDto(Long amount, AuctionDto auctionDto, DepositStatus depositStatus, Long bidPrice) {
		this.amount = amount;
		this.auctionDto = auctionDto;
		this.depositStatus = depositStatus;
		this.bidPrice = bidPrice;
	}

	public static DepositDto of(Deposit deposit){
		return DepositDto.builder()
			.amount(deposit.getAmount())
			.auctionDto(AuctionDto.of(deposit.getAuction()))
			.depositStatus(deposit.getDepositStatus())
			.bidPrice(0L) // check
			.build();
	}
}
