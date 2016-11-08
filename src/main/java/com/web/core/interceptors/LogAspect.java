package com.web.core.interceptors;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
public class LogAspect {
	private Logger logger = Logger.getLogger(getClass());

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	/**
	 * 拦截位置,拦截所有controller
	 */
	@Pointcut("execution(public * com.web.action..*.*(..))")
	public void webLog() {
	}

	/**
	 * controller前
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());

		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (null == attributes) {
			return;
		}
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		// logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
		Map<String, String[]> map = request.getParameterMap();
		StringBuffer sb = new StringBuffer();
		if (null != map && map.size() > 0) {
			for (Map.Entry<String, String[]> entry : map.entrySet()) {
				String[] values = entry.getValue();
				String value = null;
				if (values.length > 1) {
					value = Arrays.toString(entry.getValue());
				} else {
					value = values[0];
				}
				sb.append(entry.getKey() + ":" + value + ", ");
			}
		}
		logger.info("PARAMS : [" + sb.toString() + "]");
	}

	/**
	 * 操作异常记录
	 */
	@AfterThrowing(pointcut = "webLog()", throwing = "e")
	public void doAfterThrowing(JoinPoint point, Throwable e) {
		logger.info("发生异常:", e);
	}

	/**
	 * controller后
	 * 
	 * @param joinPoint
	 * @param ret
	 * @throws Throwable
	 */
	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
		logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "   SPEND TIME : "
				+ (System.currentTimeMillis() - startTime.get()));
	}
}
