package com.ssafy.web.domain.bid.service;

import org.springframework.stereotype.Service;

import com.ssafy.web.domain.bid.repository.BidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidService {
	private final BidRepository bidRepository;

}
