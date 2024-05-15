package com.ssafy.web.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.member.dto.MemberDto;
import com.ssafy.web.domain.member.dto.SignUpRequest;
import com.ssafy.web.domain.member.dto.UpdateMemberRequest;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/me")
	public ResponseEntity<?> getMember(){
		MemberDto memberDto = memberService.getMember();
		return ResponseEntity.status(HttpStatus.OK).body(memberDto);
	}

	@PostMapping
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest){
		memberService.signup(signUpRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/me")
	public ResponseEntity<?> updateMember(@Valid @RequestBody UpdateMemberRequest updateMemberRequest){
		memberService.updateMember(updateMemberRequest);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/me")
	public ResponseEntity<?> deleteMember(){
		memberService.deleteMember();
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
