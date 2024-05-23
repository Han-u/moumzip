package com.ssafy.web.global.common.db;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jakarta.persistence.OptimisticLockException;

@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
@Component
public class OptimisticLockRetryAspect {

	@Around("@annotation(retry)")
	public Object retryOptimisticLock(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
		Exception exception = null;
		for(int attempt = 0; attempt < retry.maxRetries(); attempt++){
			try {
				if(!TransactionSynchronizationManager.isActualTransactionActive()){
					return handleWithNewTransaction(joinPoint);
				} else {
					return joinPoint.proceed();
				}
			} catch (Throwable e){
				if(!(e instanceof OptimisticLockException)){
					throw e;
				}
				exception = (Exception)e;
				Thread.sleep(retry.retryDelay());
			}
		}
		throw exception;
	}

	@Transactional
	public Object handleWithNewTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
		return joinPoint.proceed();
	}
}
