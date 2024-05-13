package com.ssafy.web.domain.transaction.entity;

import java.time.LocalDateTime;

public class Transaction {
	private Long transactionId;
	private Long dongCode;
	private String address;
	private LocalDateTime contractDate;
	private float area;
	private int floor;
	private Long price;
}
