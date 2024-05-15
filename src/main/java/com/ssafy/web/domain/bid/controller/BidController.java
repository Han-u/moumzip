package com.ssafy.web.domain.bid.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.bid.service.BidService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bids")
@RequiredArgsConstructor
public class BidController {
	private final BidService bidService;

	@GetMapping
	public List<Auction> getBidList(){
		return null;
	}

	@GetMapping("/{bidId}")
	public Auction getBid(@PathVariable String bidId){
		return null;
	}

	@PostMapping
	public void createBid(){

	}

	@PatchMapping("/{bidId}")
	public void updateBid(@PathVariable String bidId){

	}
}
