package com.ssafy.web.domain.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.auction.entity.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
