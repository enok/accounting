package br.com.accounting.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingHandlerAspect extends GenericAspect {
    @Pointcut("execution(public * *(..)) && within(br.com.accounting..*)")
    public void publicMethod() {
    }

    @Before("publicMethod()")
    public void publicLogBeforeAdvice(JoinPoint joinPoint) {
        getLog(joinPoint).debug("[ {} ]", getMethodName(joinPoint));
        printArgumentsDebug(joinPoint);
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