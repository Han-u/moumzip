package com.ssafy.web.domain.deposit.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.entity.DepositStatus;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
	Optional<Deposit> findByMember_MemberIdAndAuction_AuctionId(Long memberId, Long auctionId);
	Optional<Deposit> findByMember_MemberIdAndAuction_AuctionIdAndAuction_AuctionStatusAndDepositStatus(Long memberId, Long auctionId, AuctionStatus auctionStatus, DepositStatus depositStatus);

	List<Deposit> findByMember_MemberId(Long memberId);

	boolean existsByMember_MemberIdAndDepositStatusIn(Long memberId, List<DepositStatus> depositStatusList);

	List<Deposit> findByDepositStatusIn(List<DepositStatus> statuses);
	@Modifying
	@Query("UPDATE Deposit SET depositStatus = :depositStatus where depositId in :depositList")
	int updateRefundedDeposit(DepositStatus depositStatus, List<Deposit> depositList);

	@Modifying
	@Query("")
	void updatedNotAwarded(LocalDateTime now);
}
