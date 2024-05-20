package com.ssafy.web.domain.auction.service;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.repository.AuctionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public List<Auction> findAllAuctions() {
        return auctionRepository.findAll();
    }

    public Auction findAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException(auctionId + "옥션을 찾을 수 없습니다."));
    }

    @Transactional
    public void createAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    @Transactional
    public void updateAuction(Long auctionId, Auction auction) {
        Auction currentAuction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException(auctionId + "옥션을 찾을 수 없습니다."));

        currentAuction.update(auction);
    }

    @Transactional
    public void deleteAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("Auction not found: " + auctionId));

        auctionRepository.delete(auction);
    }
}
