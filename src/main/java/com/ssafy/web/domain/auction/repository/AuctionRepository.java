package com.ssafy.web.domain.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.auction.entity.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Page<Auction> findByAuctionStatusOrderByCreatedAtDesc(String auctionStatus, Pageable pageable);

    Page<Auction> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
