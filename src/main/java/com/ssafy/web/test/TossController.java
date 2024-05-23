package com.ssafy.web.test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssafy.web.external.toss.TossVirtualAccountRequest;
import com.ssafy.web.external.toss.dto.TossVirtualAccountResponse;
import com.ssafy.web.external.toss.dto.VirtualAccount;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/toss")
@RequiredArgsConstructor
public class TossController {
	@GetMapping
	public ResponseEntity<?> test(){
		// return ResponseEntity.status(HttpStatus.OK).body(TossClient.issueVirtualAccount(null));
		return null;
	}
	@PostMapping("/virtual-accounts")
	public ResponseEntity<?> createVirtualAccount(@RequestBody TossVirtualAccountRequest request){
		// 실제 우리 서비스가 아닌 토스에서 가상계좌를 발급받는 시나리오를 위한 컨트롤러입니댜!!
		System.out.println(request);
		TossVirtualAccountResponse response = TossVirtualAccountResponse.builder()
			.orderId(request.getOrderId().toString())
			.status("OK")
			.requestedAt(LocalDateTime.now())
			.virtualAccount(VirtualAccount.builder()
				.accountNumber("21312412312")
				.accountType("일반")
				.bankCode("20")
				.customerName(request.getCustomerName())
				.dueDate(LocalDateTime.now().plusDays(7))
				.build()
			)
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestParam String orderId){
		// 사용자가 토스에서 발급받은 가상계좌에 돈을 넣었을 때의 시나리오입니다!!
		System.out.println(orderId);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("orderId", orderId);
		System.out.println(orderId);
		restTemplate.postForObject("http://localhost:8080/external/callback", attributes, String.class);
		System.out.println("요청 성공");
		return ResponseEntity.status(HttpStatus.OK).body("입금 콜백 성공");
	}

}
