package br.com.accounting.commons.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static br.com.accounting.commons.aspect.LogType.DEBUG;
import static br.com.accounting.commons.aspect.LogType.INFO;

public abstract class GenericAspect {
    protected Class<?> getClass(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass();
    }

    protected String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    protected Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(getClass(joinPoint));
    }

    private void printArguments(JoinPoint joinPoint, LogType type) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (notNullArguments(parameterNames, args)) {
            for (int i = 0; i < parameterNames.length; i++) {
                switch (type) {
                    case INFO:
                        getLog(joinPoint).info("\t{}: {}", parameterNames[i], args[i]);
                        break;
                    case DEBUG:
                        getLog(joinPoint).debug("\t{}: {}", parameterNames[i], args[i]);
                }
            }
        }
    }

    protected void printArgumentsInfo(JoinPoint joinPoint) {
        printArguments(joinPoint, INFO);
    }

    protected void printArgumentsDebug(JoinPoint joinPoint) {
        printArguments(joinPoint, DEBUG);
    }

    protected boolean notNullArguments(String[] parameterNames, Object[] args) {
        return (parameterNames != null) && (args != null);
    }

    protected String[] getParameterNames(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        return codeSignature.getParameterNames();
    }

    protected Object[] getParameterValues(JoinPoint joinPoint) {
        return joinPoint.getArgs();
    }

    protected abstract Logger getLog(JoinPoint joinPoint);
}
