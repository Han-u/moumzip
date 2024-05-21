package com.ssafy.web.domain.auction.controller;

import com.ssafy.web.domain.auction.dto.AuctionCreateUpdate;
import com.ssafy.web.domain.auction.dto.AuctionDetail;
import com.ssafy.web.domain.auction.dto.AuctionList;
import com.ssafy.web.domain.member.dto.MemberDto;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.auth.CurrentUser;
import com.ssafy.web.global.common.auth.RequiresAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.web.domain.auction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    // 몇 개 가져올지?, 파라미터로 옵션을 받아서 옵션에 따라 진행중, 뭐 어쩌고
    // spring data jpa 페이징 기능 활용, page 리턴
    // dto 처리
    @GetMapping
    public ResponseEntity<Page<AuctionList>> getAuctionList(@RequestParam(required = false) String status, Pageable pageable) {
        Page<AuctionList> auctionPage = auctionService.findAllAuctions(status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(auctionPage);
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionDetail> getAuction(@PathVariable Long auctionId) {
        AuctionDetail auctionDetail = auctionService.findAuctionById(auctionId);
        return ResponseEntity.status(HttpStatus.OK).body(auctionDetail);
    }

    @PostMapping
//    @RequiresAdmin
    public ResponseEntity<?> createAuction(@RequestBody AuctionCreateUpdate auctionCreateUpdate, MemberDto memberDto) {
        // 멤버 받아서 서비스에서 어드민 확인
        auctionService.createAuction(auctionCreateUpdate, memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{auctionId}")
    @RequiresAdmin
    public ResponseEntity<?> updateAuction(@PathVariable Long auctionId, @RequestBody AuctionCreateUpdate auctionCreateUpdate, @CurrentUser Member member) {
        // 멤버 받아서 서비스에서 어드민 확인
        auctionService.updateAuction(auctionId, auctionCreateUpdate, member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{auctionId}")
    @RequiresAdmin
    public ResponseEntity<?> deleteAuction(@PathVariable Long auctionId, @CurrentUser Member member) {
        // 멤버 받아서 서비스에서 어드민 확인
        auctionService.deleteAuction(auctionId, member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
