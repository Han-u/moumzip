package com.ssafy.web.domain.member.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.deposit.entity.DepositStatus;
import com.ssafy.web.domain.deposit.repository.DepositRepository;
import com.ssafy.web.global.util.MakeSalt;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
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
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class MemberService {
    private final MemberRepository memberRepository;
    private final DepositRepository depositRepository;
    private final Validator validator;

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

    //
    @Transactional
    public void updateMember(Member member, @Valid UpdateMemberRequest updateMemberRequest) {

        //유효성 검사
        var violations = validator.validate(updateMemberRequest);
        if(!violations.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_PARAMETER);
        }

        member.updateMember(updateMemberRequest.getPassword(), updateMemberRequest.getPhone());
    }

    @Transactional
    public void deleteMember(Member member) {
        // member 삭제 가능성 확인
        // admin
        if (member.isAdmin()) {
            throw new BusinessException(ErrorCode.USER_CANNOT_DELETE);
        }
        // 일반 회원
        // memberId로 deposit 테이블 확인 후 status가 "CANCELLED", "PENDING_DEPOSIT", "DEPOSITED"이거나 "AWARDED"인 경우 취소 불가능.
        List<DepositStatus> validStatusList = Arrays.asList(DepositStatus.DEPOSITED, DepositStatus.AWARDED, DepositStatus.CANCELLED, DepositStatus.PENDING_DEPOSIT);
        boolean hasActiveDeposits = depositRepository.existsByMember_MemberIdAndDepositStatusIn(member.getMemberId(), validStatusList);

        if (hasActiveDeposits) {
            throw new BusinessException(ErrorCode.USER_CANNOT_DELETE);
        }
        memberRepository.delete(member);
    }


    public List<MemberDto> getAllMembers(Member member) {
        if(!member.isAdmin()) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        List<Member> members = memberRepository.findAll();
        return members.stream().map(MemberDto::of).collect(Collectors.toList());
    }
}
