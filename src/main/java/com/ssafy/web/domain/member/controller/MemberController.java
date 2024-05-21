package com.ssafy.web.domain.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.member.dto.MemberDto;
import com.ssafy.web.domain.member.dto.SignUpRequest;
import com.ssafy.web.domain.member.dto.UpdateMemberRequest;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.domain.member.service.MemberService;
import com.ssafy.web.global.common.auth.CurrentUser;
import com.ssafy.web.global.common.auth.RequiresAdmin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

    @GetMapping("/me")
	public ResponseEntity<?> getMember(@CurrentUser Member member){
		MemberDto memberDto = MemberDto.of(member);
		return ResponseEntity.status(HttpStatus.OK).body(memberDto);
	}

	@PostMapping
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest){
		memberService.signup(signUpRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/me")
	public ResponseEntity<?> updateMember(@Valid @RequestBody UpdateMemberRequest updateMemberRequest, @CurrentUser Member member){
		memberService.updateMember(member, updateMemberRequest);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/me")
	public ResponseEntity<?> deleteMember(@CurrentUser Member member){
		memberService.deleteMember(member);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping
	@RequiresAdmin
	public ResponseEntity<?> getAllMembers(){
		List<MemberDto> members = memberService.getAllMembers();
		return ResponseEntity.status(HttpStatus.OK).body(members);
	}

	// 계좌 등록
}
