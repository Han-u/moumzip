package com.ssafy.web.domain.deposit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.deposit.service.DepositService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/deposits")
@RequiredArgsConstructor
public class DepositController {
	private final DepositService depositService;
	@GetMapping
	public List<Auction> getDepositList(){
		return null;
	}

	@GetMapping("/{depositId}")
	public Auction getDeposit(@PathVariable String depositId){
		return null;
	}

	@PostMapping
	public void createDeposit(){

	}
}
