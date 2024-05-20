package com.ssafy.web.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.web.global.util.MakeSalt;
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

	public MemberDto getMember(String memberEmail) {
		Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		return MemberDto.of(member);
	}

	@Transactional
	public void signup(SignUpRequest signUpRequest) {
		String salt = MakeSalt.generateSalt();
		Member member = signUpRequest.toEntity(salt);
		memberRepository.save(member);
	}

	@Transactional
	public void updateMember(Member member, UpdateMemberRequest updateMemberRequest) {
		member.updateMember(updateMemberRequest.getPassword(), updateMemberRequest.getPhone());
	}

	@Transactional
	public void deleteMember(Member member) {
		memberRepository.delete(member);
	}

	public List<MemberDto> getAllMembers() {
		List<Member> members = memberRepository.findAll();
		return members.stream().map(MemberDto::of).collect(Collectors.toList());
	}
}
