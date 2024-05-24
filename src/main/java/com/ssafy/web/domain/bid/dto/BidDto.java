package com.ssafy.web.domain.bid.dto;

import java.time.LocalDateTime;

import com.ssafy.web.domain.bid.entity.Bid;
import com.ssafy.web.domain.member.dto.MemberDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BidDto {
	private Long bidId;
	private Long bidPrice;
	private LocalDateTime createdAt;
	private MemberDto member;

	@Builder
	public BidDto(Long bidId, Long bidPrice, LocalDateTime createdAt, MemberDto member) {
		this.bidId = bidId;
		this.bidPrice = bidPrice;
		this.createdAt = createdAt;
		this.member = member;
	}

	public static BidDto of(Bid bid){
		return BidDto.builder()
			.bidId(bid.getBidId())
			.bidPrice(bid.getBidPrice())
			.createdAt(bid.getCreatedAt())
			.member(MemberDto.of(bid.getDeposit().getMember()))
			.build();
	}
}
