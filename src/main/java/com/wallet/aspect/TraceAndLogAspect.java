package com.wallet.aspect;

import java.util.Arrays;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.wallet.annotations.TraceAndLogs;
import com.wallet.trace.TraceContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@Order(1) 
public class TraceAndLogAspect {
	
	@Pointcut("execution(* com.wallet.controller.CustomerController.*(..))")
	public void controllerLayer() {}
	
	@Pointcut("execution(* com.wallet.serviceimpl.*.*(..))")
	public void serviceLayer() {}
	
	@Pointcut("execution(* com.wallet.repo.*.*(..))")
	public void repoLayer() {}

	@Pointcut("controllerLayer() || serviceLayer() || repoLayer()")
	public void allLayers() {}
	
//	@Around("@annotation(traceAndLogs)")
	@Around("allLayers() && @annotation(traceAndLogs)")
	public Object handleTraceAndLogs(ProceedingJoinPoint pjp ,TraceAndLogs traceAndLogs ) throws Throwable{
		log.info("Starting from here..............................................................");
		String traceId = UUID.randomUUID()
							.toString()
							.substring(0,8)
							.toUpperCase();
		
		TraceContext.setTraceId(traceId);
		TraceContext.setStartTime(System.currentTimeMillis());
		
		String method = pjp.getSignature().getName();
		String action = traceAndLogs.action().isEmpty()
				?method.toUpperCase()
						:traceAndLogs.action();
		
		log.info(" ACTION : {}",action);
		log.info("TRACE ID : {}",traceId);
		log.info("METHOD : {}",method);
		log.info("REQUEST : {}",Arrays.toString(pjp.getArgs()));
		log.info("TIMESTAMP : {}",java.time.LocalDateTime.now());
		
		try {
			Object result = pjp.proceed();
			long timeTaken = System.currentTimeMillis()- TraceContext.getStartTime();
			
			log.info(" STATUS : Success");
			log.info("TRACE ID : {}",traceId);
			log.info(" ACTION : {}",action);
			log.info(" RESPONSE : {}",result);
			log.info(" TIME TAKEN : {}",timeTaken);
			
			return result;
			
		}catch(Throwable ex) {
			log.info("Getting error..............................................................");
			long timeTaken = System.currentTimeMillis()-TraceContext.getStartTime();
			
			log.error(" STATUS : Failure");
			log.error("TRACE ID : {}",traceId);
			log.error(" ACTION : {}",action);
			log.error(" RESPONSE : ApiResponse(code=ERROR, message={}, type={})",
		            ex.getMessage(),
		            ex.getClass().getSimpleName());
//			log.error(" ERROR : {}",ex.getMessage());
//			log.error("TYPE : {}", ex.getClass().getSimpleName());
			log.error(" TIME TAKEN : {}",timeTaken);
			
			throw ex;
		}finally {
			TraceContext.clear();
		}
//		return action;
	}
	
	@Before("allLayers()")
	public void logLayerEntry(JoinPoint jp) {
		String traceId = TraceContext.getTraceId();
		if(traceId !=null) {
			log.info("[{}] ──► {} → {}() | args: {}",traceId,jp.getTarget().getClass().getSimpleName(),
					jp.getSignature().getName(),
					Arrays.toString(jp.getArgs())
					);
		}
	}
	
	@AfterReturning(pointcut="allLayers()",returning="result")
	public void logLayerExit(JoinPoint jp, Object result) {
		String traceId =TraceContext.getTraceId();
		if(traceId!= null) {
			log.info("[{}] ◄── {} → {}() | returned: {}",traceId,
					jp.getTarget().getClass().getSimpleName(),
					jp.getSignature().getName(),
					result
					);
		}
	}
	
	@AfterThrowing(pointcut="allLayers()", throwing="ex")
	public void logLayerException(JoinPoint jp, Exception ex) {
	    String traceId = TraceContext.getTraceId();

	    if (traceId != null) {
	        log.error("[{}] ◄── {} → {}() | status: FAILED | threw: {} : {}",
	                traceId,
	                jp.getTarget().getClass().getSimpleName(),
	                jp.getSignature().getName(),
	                ex.getClass().getSimpleName(),
	                ex.getMessage());
	    }
	}
	
//	@AfterThrowing(pointcut="allLayers()", throwing="ex")
//	public void logLayerException(JoinPoint jp, Exception ex) {
//		String traceId = TraceContext.getTraceId();
//		
//		if(traceId != null) {
//			log.error("[{}] ✖── {} → {}() | threw: {}",traceId,
//					jp.getTarget().getClass().getSimpleName(),
//					jp.getSignature().getName(),
//					ex.getMessage());
//		}
//	}
	
	
}
