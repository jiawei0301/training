// src/main/java/com/example/springpractice/aop/ExceptionHandlingAspect.java
package com.example.springpractice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ExceptionHandlingAspect {
    private static final Logger logger = Logger.getLogger(ExceptionHandlingAspect.class.getName());

    @Around("within(com.example.springpractice.controller..*) || within(com.example.springpractice.service..*)")
    public Object handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            logger.severe("Handled by AOP: Exception in " + joinPoint.getSignature() + " - " + ex.getMessage());
            throw ex; // Rethrow so that Spring's exception handler can catch and show friendly page
        }
    }
}
