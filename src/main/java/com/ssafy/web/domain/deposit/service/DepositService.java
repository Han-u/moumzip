package com.ssafy.web.domain.deposit.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.ssafy.web.external.toss.TossClient;
import com.ssafy.web.external.toss.TossVirtualAccountRequest;
import com.ssafy.web.global.common.util.OtpValidator;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepositService {
	private final AuctionRepository auctionRepository;
	private final DepositRepository depositRepository;
	private final OtpValidator otpValidator;
	private final TossClient tossClient;

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
		TossVirtualAccountRequest body = TossVirtualAccountRequest.builder()
			.amount(auction.getDepositPrice())
			.bank(member.getBank())
			.customerName(member.getName())
			.orderId(auctionId+"_"+ member.getMemberId())
			.orderName(auction.getLocation().length() > 20 ? auction.getLocation().substring(0, 20) : auction.getLocation())
			.build();

		String virtualAccount = tossClient.issueVirtualAccount(body);

		Deposit participation = Deposit.builder()
			.amount(auction.getDepositPrice())
			.member(member)
			.auction(auction)
			.depositStatus(DepositStatus.PENDING_DEPOSIT)
			.bank(member.getBank())
			.virtualAccount(virtualAccount)
			.build();

		depositRepository.save(participation);
	}

	@Transactional
	public void setOtp(Member member, Long auctionId, OtpRequest otpRequest) {
		// 경매 시작됐을 때만 세팅 가능
		Deposit deposit = depositRepository.findByMember_MemberIdAndAuction_AuctionIdAndAuction_AuctionStatusAndDepositStatus(
			member.getMemberId(),
			auctionId,
			AuctionStatus.PROGRESS,
			DepositStatus.DEPOSITED
		).orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));

		if(!otpValidator.isValidPassword(otpRequest.getOtp())){
			throw new BusinessException(ErrorCode.BAD_REQUEST);
		}

		deposit.updateOtp(otpRequest.getOtp());
		depositRepository.save(deposit);
	}

	public List<DepositDto> getMyAuctions(Member member) {
		List<Deposit> myAuctions = depositRepository.findByMember_MemberId(member.getMemberId());
		return myAuctions.stream().map(DepositDto::of).collect(Collectors.toList());
	}

	public List<Deposit> getRefundDeposit(){
		List<DepositStatus> statuses = new ArrayList<>();
		statuses.add(DepositStatus.CANCELED);
		statuses.add(DepositStatus.NOT_AWARDED);
		return depositRepository.findByDepositStatusIn(statuses);
	}

	@Transactional
	public void updateRefundedDeposits(DepositStatus depositStatus, List<Deposit> depositList){
		depositRepository.updateRefundedDeposit(depositStatus, depositList);
	}

	@Transactional
	public void updateNotAwarded(LocalDateTime now) {
		depositRepository.updatedNotAwarded(now);
	}
}
