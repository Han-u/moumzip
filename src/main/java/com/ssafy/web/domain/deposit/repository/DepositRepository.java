package com.ssafy.web.domain.deposit.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.web.domain.deposit.entity.DepositStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.entity.DepositStatus;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
	Optional<Deposit> findByMember_MemberIdAndAuction_AuctionId(Long memberId, Long auctionId);
	Optional<Deposit> findByMember_MemberIdAndAuction_AuctionIdAndAuction_AuctionStatusAndDepositStatus(Long memberId, Long auctionId, AuctionStatus auctionStatus, DepositStatus depositStatus);

	List<Deposit> findByMember_MemberId(Long memberId);

	boolean existsByMember_MemberIdAndDepositStatusIn(Long memberId, List<DepositStatus> depositStatusList);

	boolean existsByAuction_AuctionIdAndDepositStatusIn(Long AuctionId, List<DepositStatus> depositStatusList);
}
