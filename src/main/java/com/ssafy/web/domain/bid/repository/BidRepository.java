package com.ssafy.web.domain.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.bid.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
