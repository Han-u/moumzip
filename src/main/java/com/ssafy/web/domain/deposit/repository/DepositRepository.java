package com.ssafy.web.domain.deposit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.entity.DepositStatus;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
	Optional<Deposit> findByMember_MemberIdAndAuction_AuctionId(Long memberId, Long auctionId);
	Optional<Deposit> findByMember_MemberIdAndAuction_AuctionStatusAndDepositStatus(Long memberId, Long auctionId, AuctionStatus auctionStatus, DepositStatus depositStatus);

	List<Deposit> findByMember_MemberId(Long memberId);
}
