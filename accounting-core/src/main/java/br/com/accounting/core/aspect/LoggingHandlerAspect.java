package br.com.accounting.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingHandlerAspect extends GenericAspect {
    @Pointcut("execution(public * *(..)) && within(br.com.accounting..*)")
    public void publicMethod() {
    }

    @Before("publicMethod()")
    public void publicLogBeforeAdvice(JoinPoint joinPoint) {
        log = getLogger(joinPoint);
        log.debug("[ {} ]", getMethodName(joinPoint));
        printArgumentsDebug(joinPoint);
    }

    @AfterReturning(pointcut = "publicMethod()", returning = "result")
    public void logAfterReturningAdvice(JoinPoint joinPoint, Object result) {
        log = getLogger(joinPoint);
        log.trace("\t\tresult: {}", result);
    }

    @AfterThrowing(pointcut = "publicMethod()", throwing = "e")
    public void publicLogAfterThrowingAdvice(JoinPoint joinPoint, Exception e) {
        log = getLogger(joinPoint);
        log.error("Erro: " + e.getMessage(), e);
    }
}