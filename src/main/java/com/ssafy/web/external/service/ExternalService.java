package com.ssafy.web.external.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.entity.DepositStatus;
import com.ssafy.web.domain.deposit.repository.DepositRepository;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExternalService {
	private final DepositRepository depositRepository;

	@Transactional
	public void depositCallback(Long orderId) {
		Deposit deposit = depositRepository.findById(orderId).orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));
		deposit.setDepositStatus(DepositStatus.DEPOSITED);
	}
}
