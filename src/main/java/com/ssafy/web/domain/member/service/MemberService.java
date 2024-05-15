package com.ssafy.web.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.web.domain.member.dto.MemberDto;
import com.ssafy.web.domain.member.dto.SignUpRequest;
import com.ssafy.web.domain.member.dto.UpdateMemberRequest;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.domain.member.repository.MemberRepository;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;

	public MemberDto getMember() {
		String email = "";
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		return MemberDto.of(member);
	}

	@Transactional
	public void signup(SignUpRequest signUpRequest) {
		String salt = "";
		Member member = signUpRequest.toEntity(salt);
		memberRepository.save(member);
	}

	@Transactional
	public void updateMember(UpdateMemberRequest updateMemberRequest) {
		String email = "";
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		member.updateMember(updateMemberRequest.getPassword(), updateMemberRequest.getPhone());
	}

	@Transactional
	public void deleteMember() {
		String email = "";
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		memberRepository.delete(member);
	}
}
