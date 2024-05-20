package com.ssafy.web.domain.bid.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.bid.dto.BidDto;
import com.ssafy.web.domain.bid.dto.BidRequest;
import com.ssafy.web.domain.bid.service.BidService;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.auth.CurrentUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auctions/{auctionId}/bids")
@RequiredArgsConstructor
public class BidController {
	private final BidService bidService;

	@GetMapping
	public ResponseEntity<?> getBidList(@PathVariable Long auctionId) {
		List<BidDto> bidList = bidService.getBidList(auctionId);
		return ResponseEntity.status(HttpStatus.OK).body(bidList);
	}

	@PostMapping
	public ResponseEntity<?> createBid(@CurrentUser Member member, @PathVariable Long auctionId, BidRequest bidRequest){
		bidService.createBid(member, auctionId, bidRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
