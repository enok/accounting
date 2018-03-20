package br.com.accounting.core.aspect;

import ch.qos.logback.classic.LoggerContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingHandlerAspect {
    private static Logger log = LoggerFactory.getLogger(LoggingHandlerAspect.class);

    private static final String PUBLIC_METHOD = "execution(public * *(..)) && within(br.com.accounting..*)";

    @Before(value = PUBLIC_METHOD)
    public void publicLogBeforeAdvice(JoinPoint joinPoint) {
        log = getLogger(joinPoint);
        log.debug("[ {} ]", getMethodName(joinPoint));
        printArgumentsDebug(joinPoint);
    }

    @AfterReturning(pointcut = PUBLIC_METHOD, returning = "result")
    public void logAfterReturningAdvice(JoinPoint joinPoint, Object result) {
        log = getLogger(joinPoint);
        log.trace("\t\tresult: {}", result);
    }

    @AfterThrowing(pointcut = PUBLIC_METHOD, throwing = "e")
    public void publicLogAfterThrowingAdvice(JoinPoint joinPoint, Exception e) {
        log = getLogger(joinPoint);
        log.error("Erro: " + e.getMessage(), e);
    }

    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    private void printArgumentsDebug(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (notNullArguments(parameterNames, args)) {
            for (int i = 0; i < parameterNames.length; i++) {
                log.debug("\t{}: {}", parameterNames[i], args[i]);
            }
        }
    }

    private boolean notNullArguments(String[] parameterNames, Object[] args) {
        return (parameterNames != null) && (args != null);
    }

    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    }
}
