package com.ssafy.web.external.toss.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VirtualAccount {
	private String accountNumber;
	private String accountType;
	private String bankCode;
	private String customerName;
	private LocalDateTime dueDate;

	@Builder
	public VirtualAccount(String accountNumber, String accountType, String bankCode, String customerName,
		LocalDateTime dueDate) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.bankCode = bankCode;
		this.customerName = customerName;
		this.dueDate = dueDate;
	}
}
