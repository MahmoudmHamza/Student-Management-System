package com.hamza.student_management_system.core.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    @Pointcut("execution(* com.hamza.student_management_system.course.services..*(..))")
    void allCourseServices() {

    }

    @Pointcut("execution(* com.hamza.student_management_system.user.services..*(..))")
    void allUserServices() {

    }

    @Around("allUserServices || allUserServices")
    Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        log.info("Method " + joinPoint.getSignature().getName() + " execution time: " + elapsedTime + " milliseconds.");
        return result;
    }
}
