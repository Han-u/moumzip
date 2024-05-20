package com.ssafy.web.domain.bid.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.auction.entity.AuctionType;
import com.ssafy.web.domain.bid.dto.BidDto;
import com.ssafy.web.domain.bid.dto.BidRequest;
import com.ssafy.web.domain.bid.entity.Bid;
import com.ssafy.web.domain.bid.repository.BidRepository;
import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.repository.DepositRepository;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BidService {
	private final BidRepository bidRepository;
	private final DepositRepository depositRepository;

	public List<BidDto> getBidList(Long auctionId) {
		List<Bid> bids = bidRepository.findByDeposit_Auction_AuctionId(auctionId);
		return bids.stream().map(BidDto::of).collect(Collectors.toList());
	}

	@Transactional
	public void createBid(Member member, Long auctionId, BidRequest bidRequest) {
		// 입찰 자격 있는지 확인
		Deposit deposit = depositRepository.findByMember_MemberIdAndAuction_AuctionId(member.getMemberId(), auctionId)
			.orElseThrow(() -> new BusinessException(ErrorCode.AUCTION_NOT_QUALIFIED));
		Auction auction = deposit.getAuction();
		// 경매 열려있는지
		if(auction.getAuctionStatus() != AuctionStatus.PROGRESS){
			throw new BusinessException(ErrorCode.AUCTION_NOT_IN_PROGRESS);
		}

		// 호가경매라면 여러번 가능, 아니라면 1번만 가능
		if(auction.getAuctionType() != AuctionType.ENGLISH &&
		deposit.getBid().size() > 0){
			throw new BusinessException(ErrorCode.AUCTION_ALREADY_PARTICIPATED);
		}

		// 현재 최고가보다 높으면 갱신
		if(auction.getWinningBidPrice() < bidRequest.getBidPrice()){
			auction.updateWinner(bidRequest.getBidPrice(), member);
		}

		// 호가경매, 끝날때 되면 시간 증가
		if(auction.getAuctionType() == AuctionType.ENGLISH){
			// 시간 증가될 때
			// 얼마나 증가?
		}
	}
}
