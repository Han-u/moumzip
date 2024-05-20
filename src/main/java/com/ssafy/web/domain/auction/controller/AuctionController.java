package com.ssafy.web.domain.auction.controller;

import java.util.List;

import com.ssafy.web.global.common.auth.RequiresAdmin;
import org.springframework.web.bind.annotation.*;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping
    public List<Auction> getAuctionList() {
        return auctionService.findAllAuctions();
    }

    @GetMapping("/{auctionId}")
    public Auction getAuction(@PathVariable Long auctionId) {
        return auctionService.findAuctionById(auctionId);
    }

    @RequiresAdmin
    @PostMapping
    public void createAuction(@RequestBody Auction auction) {
		auctionService.createAuction(auction);
    }

    @RequiresAdmin
    @PatchMapping("/{auctionId}")
    public void updateAuction(@PathVariable Long auctionId, @RequestBody Auction auction) {
        auctionService.updateAuction(auctionId, auction);
    }

    @RequiresAdmin
    @DeleteMapping("/{auctionId}")
    public void deleteAuction(@PathVariable Long auctionId) {
        auctionService.deleteAuction(auctionId);
    }
}
