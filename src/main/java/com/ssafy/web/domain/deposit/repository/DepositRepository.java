package com.ssafy.web.domain.deposit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.deposit.entity.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
	Optional<Deposit> findByMember_MemberIdAndAuction_AuctionId(Long memberId, Long auctionId);

	List<Deposit> findByMember_MemberId(Long memberId);
}
