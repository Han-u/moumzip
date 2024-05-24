package com.ssafy.web.domain.auction.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.entity.AuctionStatus;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Page<Auction> findByAuctionStatusOrderByCreatedAtDesc(AuctionStatus auctionStatus, Pageable pageable);

    Page<Auction> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Modifying
	@Query("UPDATE Auction SET auctionStatus = :targetStatus where auctionStatus = :sourceStatus and bidOpening <= :start")
	int updateStatusByDuration(AuctionStatus sourceStatus, AuctionStatus targetStatus, LocalDateTime start);

	@Modifying
	@Query("UPDATE Auction SET auctionStatus = :targetStatus where auctionStatus = :sourceStatus and bidClosingExtended <= :now")
	int updateAuctionsEndingBefore(AuctionStatus sourceStatus, AuctionStatus targetStatus, LocalDateTime now);
}
