package br.com.accounting.commons.aspect;

import br.com.accounting.commons.service.HistoricoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class HistoryAspect extends GenericAspect {
    @Autowired
    private HistoricoService historicoService;

    @Pointcut("execution(@br.com.accounting.commons.annotation.History * *(..))")
    public void saveHistoryMethod() {
    }

    @AfterReturning("saveHistoryMethod()")
    public void saveHistory(JoinPoint joinPoint) {
        try {
            String metodo = getClass(joinPoint).getName() + "." + getMethodName(joinPoint) + "()";
            Map<String, Object> parametros = buscarParametros(joinPoint);
            getLog(joinPoint).trace("<< salvando historico >>");
            getLog(joinPoint).trace("metodo: {}", metodo);
            getLog(joinPoint).trace("parametros: {}", parametros);
            historicoService.salvar(metodo, parametros);
        }
        catch (Exception e) {
            getLog(joinPoint).warn("Não foi possível salvar o histórico.", e);
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

    @Override
    protected Logger getLog(JoinPoint joinPoint) {
        return getLogger(joinPoint);
    }
}