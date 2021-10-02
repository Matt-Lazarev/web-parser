package com.project.webparser.aop;

import com.project.webparser.entity.Website;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingWebsiteController {

    @Pointcut("execution(public String saveWebsiteUrl(..))")
    private void saveWebsiteMethod(){}

    @Around("saveWebsiteMethod()")
    public Object aroundSaveWebsiteAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String url = UrlUtils.getWebsiteUrl(joinPoint);
        log.info("Trying to save '{}' url to DB 'websites'", url);
        Object result = null;
        try {
            result = joinPoint.proceed();
            log.info("Website '{}' successfully saved to DB 'websites'", url);
        } catch (Throwable ex) {
            log.error("Exception occurred during saving '{}'. ERROR: {}", url, ex.getMessage());
            throw ex;
        }
        return result;
    }
}
