package com.ssafy.web.domain.bid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.bid.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
	List<Bid> findByDeposit_Auction_AuctionId(Long auctionId);
}
