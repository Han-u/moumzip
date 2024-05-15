package com.ssafy.web.domain.auction.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {
	private final AuctionService auctionService;

	@GetMapping
	public List<Auction> getAuctionList(){
		return null;
	}

	@GetMapping("/{auctionId}")
	public Auction getAuction(@PathVariable String auctionId){
		return null;
	}

	@PostMapping
	public void createAuction(){

	}

	@PatchMapping("/{auctionId}")
	public void updateAuction(@PathVariable String auctionId){

	}

	@DeleteMapping("/{auctionId}")
	public void deleteAuction(@PathVariable String auctionId){

	}
}
