package com.ssafy.web.domain.deposit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.deposit.dto.DepositDto;
import com.ssafy.web.domain.deposit.dto.OtpRequest;
import com.ssafy.web.domain.deposit.service.DepositService;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.auth.CurrentUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class DepositController {
	private final DepositService depositService;

	@PostMapping("/{auctionId}/deposit")
	public ResponseEntity<?> auctionParticipation(@CurrentUser Member member, @PathVariable Long auctionId){
		depositService.createDeposit(member, auctionId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{auctionId}/otp")
	public ResponseEntity<?> setAuctionOtp(@CurrentUser Member member, @PathVariable Long auctionId, @RequestBody OtpRequest otpRequest){
		depositService.setOtp(member, auctionId, otpRequest);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/my")
	public ResponseEntity<?> getMyAuctions(@CurrentUser Member member){
		List<DepositDto> myAuctions = depositService.getMyAuctions(member);
		return ResponseEntity.status(HttpStatus.OK).body(myAuctions);
	}

}
