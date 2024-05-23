package com.ssafy.web.global.scheduler;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.web.domain.auction.entity.AuctionStatus;
import com.ssafy.web.domain.auction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuctionScheduler {
	private final AuctionService auctionService;
	private final Logger logger = LoggerFactory.getLogger(AuctionScheduler.class);

	@Scheduled(cron = "0 0 9-21 * * *")
	public void updateAuctionStatus(){
		LocalDateTime now = LocalDateTime.now();
		int started = auctionService.updateAuctionStatus(AuctionStatus.SCHEDULED, AuctionStatus.PROGRESS, now);
		logger.info("Time: {}, updated: {}", now, started);
	}

	@Scheduled(cron = "0 */1 10-23 * * *")
	public void checkAndEndAuctions() {
		LocalDateTime now = LocalDateTime.now();
		int closed = auctionService.updateAuctionsEndingBefore(AuctionStatus.PROGRESS, AuctionStatus.ENDED, now);

		logger.info("Time: {}, updated: {}", now, closed);
	}
}
