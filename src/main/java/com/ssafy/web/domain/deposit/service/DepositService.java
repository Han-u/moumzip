package com.ssafy.web.domain.deposit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.auction.repository.AuctionRepository;
import com.ssafy.web.domain.deposit.dto.DepositDto;
import com.ssafy.web.domain.deposit.dto.OtpRequest;
import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.entity.DepositStatus;
import com.ssafy.web.domain.deposit.repository.DepositRepository;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepositService {
	private final AuctionRepository auctionRepository;
	private final DepositRepository depositRepository;

	@Transactional
	public void createDeposit(Member member, Long auctionId) {
		if (member.isAdmin() || member.getAccountNumber() == null || member.getAccountNumber().isBlank()) {
			throw new BusinessException(ErrorCode.BAD_REQUEST);
		}

		Optional<Deposit> deposit = depositRepository.findByMember_MemberIdAndAuction_AuctionId(member.getMemberId(), auctionId);
		if (deposit.isPresent()) {
			throw new BusinessException(ErrorCode.BAD_REQUEST);
		}

		Auction auction = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));
		if (!auction.isWithinParticipationPeriod()) {
			throw new BusinessException(ErrorCode.BAD_REQUEST);
		}

		// 가상 계좌 발급

		Deposit participation = Deposit.builder()
			.amount(auction.getDepositPrice())
			.member(member)
			.auction(auction)
			.depositStatus(DepositStatus.PENDING_DEPOSIT)
			.build();

		depositRepository.save(participation);
	}

	@Transactional
	public void setOtp(Member member, Long auctionId, OtpRequest otpRequest) {
		Deposit deposit = depositRepository.findByMember_MemberIdAndAuction_AuctionStatusAndDepositStatus(
			member.getMemberId(),
			auctionId,
			AuctionStatus.PROGRESS,
			DepositStatus.DEPOSITED
		).orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));

		deposit.updateOtp(otpRequest.getOtp());
		depositRepository.save(deposit);
	}

	public List<DepositDto> getMyAuctions(Member member) {
		List<Deposit> myAuctions = depositRepository.findByMember_MemberId(member.getMemberId());
		return myAuctions.stream().map(DepositDto::of).collect(Collectors.toList());
	}
}
