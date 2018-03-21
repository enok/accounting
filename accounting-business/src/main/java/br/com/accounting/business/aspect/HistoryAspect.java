package br.com.accounting.business.aspect;

import br.com.accounting.core.aspect.GenericAspect;
import br.com.accounting.core.service.HistoricoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class HistoryAspect extends GenericAspect {
    @Autowired
    private HistoricoService historicoService;

    @Pointcut("execution(@br.com.accounting.business.annotation.History * *(..))")
    public void saveHistoryMethod() {
    }

    @AfterReturning("saveHistoryMethod()")
    public void saveHistory(JoinPoint joinPoint) {
        try {
            String metodo = getMethodName(joinPoint);
            Map<String, Object> parametros = buscarParametros(joinPoint);
            historicoService.salvar(metodo, parametros);
        }
        catch (Exception e) {
            log = getLogger(joinPoint);
            log.warn("Não foi possível salvar o histórico.", e);
        }
    }

    private Map<String, Object> buscarParametros(JoinPoint joinPoint) {
        Map<String, Object> parametros = new HashMap<>();

        String[] parameterNames = getParameterNames(joinPoint);
        Object[] args = getParameterValues(joinPoint);

        if (notNullArguments(parameterNames, args)) {
            for (int i = 0; i < parameterNames.length; i++) {
                parametros.put(parameterNames[i], args[i]);
            }
        }
        return parametros;
    }
}