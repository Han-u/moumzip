package com.ssafy.web.global.scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.entity.DepositStatus;
import com.ssafy.web.domain.deposit.service.DepositService;
import com.ssafy.web.external.toss.TossClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DepositScheduler {
	private final TossClient tossClient;
	private final DepositService depositService;

	@Scheduled(cron = "0 0 7 * * *")
	public void refundDeposit(){
		List<Deposit> depositList = depositService.getRefundDeposit();
		List<Deposit> canceledDeposit = new ArrayList<>();
		List<Deposit> notAwardedDeposit = new ArrayList<>();

		for(Deposit deposit: depositList){
			String response = tossClient.refundPayment(deposit.getAuction().getAuctionId()+"_"+deposit.getMember().getMemberId(),
				deposit.getDepositStatus().getDescription());
			if("DONE".equals(response)){
				if(deposit.getDepositStatus() == DepositStatus.CANCELED){
					canceledDeposit.add(deposit);
				} else {
					notAwardedDeposit.add(deposit);
				}
			} // FIXME: else 어디감??
		}
		// FIXME: 그러게?
		depositService.updateRefundedDeposits(DepositStatus.REFUNDED_CANCEL, canceledDeposit);
		depositService.updateRefundedDeposits(DepositStatus.REFUNDED_NOT_AWARDED, notAwardedDeposit);
	}

	@Scheduled(cron = "0 10 10-23 * * *")
	public void updateNotAwarded(){
		// TODO: 진짜 최고가인지 확인 로직 추가
		LocalDateTime now = LocalDateTime.now().minusMinutes(10).withSecond(0).withNano(0);
		depositService.updateNotAwarded(now);
	}

	// TODO: 취소된 경매 상태 바꾸기
}
