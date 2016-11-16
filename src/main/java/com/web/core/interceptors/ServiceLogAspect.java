package com.web.core.interceptors;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志拦截器,拦截controller前后
 * 
 * @author guolong.qu
 * @date 2016年9月18日
 */
@SuppressWarnings("ArgNamesErrorsInspection")
@Aspect
@Order(5)
@Component
public class ServiceLogAspect {
	private Logger logger = Logger.getLogger(getClass());

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	/**
	 * 拦截位置,拦截所有controller
	 */
	@Pointcut("execution(public * com.web.service..*.*(..))")
	public void serviceLog() {
	}

	/**
	 * service前
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("serviceLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
	}

	/**
	 * 操作异常记录
	 */
	@AfterThrowing(pointcut = "serviceLog()", throwing = "e")
	public void doAfterThrowing(JoinPoint point, Throwable e) {
		logger.info("发生异常:", e);
	}

	/**
	 * service后
	 * 
	 * @param joinPoint
	 * @param ret
	 * @throws Throwable
	 */
	@AfterReturning(returning = "ret", pointcut = "serviceLog()")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
		logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + " SERVICE SPEND TIME : "
				+ (System.currentTimeMillis() - startTime.get()));
	}
}
