package com.project.webparser.aop;

import com.project.webparser.entity.Website;
import org.aspectj.lang.JoinPoint;

public class UrlUtils {
    static String getWebsiteUrl(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Website website = null;
        for(Object arg : args){
            if(arg instanceof Website){
                website = (Website) arg;
            }
        }
        return website == null ? "null" : website.getUrl();
    }

    static String getParamUrl(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String url = null;
        for(Object arg : args){
            if(arg instanceof String){
                url = (String) arg;
            }
        }
        return url == null? "null" : url;
    }
}
