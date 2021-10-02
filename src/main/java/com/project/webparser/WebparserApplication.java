package com.project.webparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WebparserApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebparserApplication.class, args);
    }

}
