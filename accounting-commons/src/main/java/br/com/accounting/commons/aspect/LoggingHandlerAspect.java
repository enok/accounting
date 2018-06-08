package br.com.accounting.commons.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class LoggingHandlerAspect extends GenericAspect {
    @Pointcut("execution(@br.com.accounting.commons.annotation.Log * *(..))")
    public void publicMethodInfo() {
    }

    @Pointcut("execution(public * *(..)) && within(br.com.accounting..*)")
    public void publicMethod() {
    }

    @Before("publicMethodInfo()")
    public void publicInfoLogBeforeAdvice(JoinPoint joinPoint) {
        getLog(joinPoint).info("[ {} ]", getMethodName(joinPoint));
        printArgumentsInfo(joinPoint);
    }

    @Before("publicMethod()")
    public void publicLogBeforeAdvice(JoinPoint joinPoint) {
        getLog(joinPoint).debug("[ {} ]", getMethodName(joinPoint));
        printArgumentsDebug(joinPoint);
    }

    @AfterReturning(pointcut = "publicMethodInfo()", returning = "result")
    public void logInfoAfterReturningAdvice(JoinPoint joinPoint, Object result) {
        getLog(joinPoint).info("\t\tresult: {}", result);
    }

    @AfterReturning(pointcut = "publicMethod()", returning = "result")
    public void logAfterReturningAdvice(JoinPoint joinPoint, Object result) {
        getLog(joinPoint).trace("\t\tresult: {}", result);
    }

    @AfterThrowing(pointcut = "publicMethod()", throwing = "e")
    public void publicLogAfterThrowingAdvice(JoinPoint joinPoint, Exception e) {
        getLog(joinPoint).error("Erro: " + e.getMessage(), e);
    }

    @Override
    protected Logger getLog(JoinPoint joinPoint) {
        return getLogger(joinPoint);
    }
}