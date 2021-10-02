package com.project.webparser.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingStatisticsController {
    @Pointcut("execution(public com.project.webparser.dto.WebsiteWordCountDTO showStatistics(..))")
    private void showStatisticsMethod(){}

    @Around("showStatisticsMethod()")
    public Object aroundShowStatisticsAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String url = UrlUtils.getParamUrl(joinPoint);
        log.info("Trying to get statistics for '{}' website", url);
        Object result = null;
        try {
            result = joinPoint.proceed();
            log.info("Statistics for '{}' successfully sent", url);
        } catch (Throwable ex) {
            log.error("Exception occurred during getting statistics for '{}'. ERROR: {}", url, ex.getMessage());
            throw ex;
        }
        return result;
    }
}
