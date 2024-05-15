package com.ssafy.web.domain.deposit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.deposit.entity.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
}
