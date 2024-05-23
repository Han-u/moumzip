package com.ssafy.web.domain.auction.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.web.domain.auction.dto.AuctionCreateUpdate;
import com.ssafy.web.domain.auction.dto.AuctionDetail;
import com.ssafy.web.domain.auction.dto.AuctionList;
import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.auction.repository.AuctionRepository;
import com.ssafy.web.domain.deposit.repository.DepositRepository;
import com.ssafy.web.domain.member.dto.MemberDto;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final DepositRepository depositRepository;

    public Page<AuctionList> findAllAuctions(String status, Pageable pageable) {
        if (status != null) {
            return auctionRepository.findByAuctionStatusOrderByCreatedAtDesc(AuctionStatus.valueOf(status), pageable)
                    .map(AuctionList::of);
        }
        return auctionRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(AuctionList::of);
    }

    public AuctionDetail findAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .map(AuctionDetail::of)
                .orElseThrow(() -> new BusinessException(ErrorCode.AUCTION_NOT_FOUND));
    }

    @Transactional
    public void createAuction(AuctionCreateUpdate auctionCreateUpdate, MemberDto memberDto) {
        if (!MemberDto.toEntity(memberDto).isAdmin()) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        // 유효한 시간인지 검증
        // bidOpening 14일 이후인지
        // bidClosing 텀이 2시간 이상인지 확인
        // 5개 뺀 DTO
        validateAuctionTime(auctionCreateUpdate.getBidOpening(), auctionCreateUpdate.getBidClosing());

        auctionRepository.save(auctionCreateUpdate.toEntity());
    }

    @Transactional
    public void updateAuction(Long auctionId, AuctionCreateUpdate auctionCreateUpdate, Member member) {
        if (!member.isAdmin()) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        // 등록일 createdAt 기준 +5일 까지 수정 가능.
        // 그 이후 부터는 삭제!
        // 생성 데이터와 비슷
        Auction currentAuction = auctionRepository.findById(auctionId).orElseThrow(() -> new BusinessException(ErrorCode.AUCTION_NOT_FOUND));
        if (currentAuction.getCreatedAt().isBefore(LocalDateTime.now().minusDays(5))) {
            throw new BusinessException(ErrorCode.AUCTION_CANNOT_UPDATE);
        }
        validateAuctionTime(auctionCreateUpdate.getBidOpening(), auctionCreateUpdate.getBidClosing());

        currentAuction.update(auctionCreateUpdate.toEntity());
    }

    // 보증금 있는지 확인할 필요 없음. 일단 취소하고 스케줄링으로 취소된 보증금 돌려주면 됨
    // createAt 5일 이후부터 취소 가능하다. 경매 "bidOpening" 2시간 전까지 가능.
    // Soft delete하자. 상태만 취소상태로 바꾸면 된다.
    @Transactional
    public void deleteAuction(Long auctionId, Member member) {
        // 관리자 권한 확인
        if (!member.isAdmin()) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        Auction auction = findAuctionById(auctionId).toEntity();

        if (auction.getCreatedAt().isBefore(LocalDateTime.now().minusDays(5)) ||
                auction.getBidOpening().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BusinessException(ErrorCode.AUCTION_CANNOT_DELETE);
        }
        // 경매 삭제

        auctionRepository.delete(auction);
    }

    private void validateAuctionTime(LocalDateTime bidOpening, LocalDateTime bidClosing) {
        if (bidOpening.isBefore(LocalDateTime.now().plusDays(14))) {
            throw new BusinessException(ErrorCode.AUCTION_INVALID_TIME);
        }

        if (bidClosing.isBefore(bidOpening.plusHours(2))) {
            throw new BusinessException(ErrorCode.AUCTION_INVALID_TIME);
        }
    }

    @Transactional
    public int updateAuctionStatus(AuctionStatus sourceStatus, AuctionStatus targetStatus, LocalDateTime start){
        return auctionRepository.updateStatusByDuration(sourceStatus, targetStatus, start);
    }

    @Transactional
    public int updateAuctionsEndingBefore(AuctionStatus sourceStatus, AuctionStatus targetStatus, LocalDateTime now) {
        return auctionRepository.updateAuctionsEndingBefore(sourceStatus, targetStatus, now);
    }
}
