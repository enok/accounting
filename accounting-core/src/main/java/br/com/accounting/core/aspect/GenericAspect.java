package br.com.accounting.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericAspect {
    protected static Logger log = LoggerFactory.getLogger(GenericAspect.class);

    protected Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    }

    protected String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    protected void printArgumentsDebug(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (notNullArguments(parameterNames, args)) {
            for (int i = 0; i < parameterNames.length; i++) {
                log.debug("\t{}: {}", parameterNames[i], args[i]);
            }
        }
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
}
