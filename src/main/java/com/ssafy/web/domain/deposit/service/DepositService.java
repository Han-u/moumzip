package com.ssafy.web.domain.deposit.service;

import org.springframework.stereotype.Service;

import com.ssafy.web.domain.deposit.repository.DepositRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositService {
	private final DepositRepository depositRepository;
}
