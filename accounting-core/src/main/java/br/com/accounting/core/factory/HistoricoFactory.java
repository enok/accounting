package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Historico;

import java.util.HashMap;
import java.util.Map;

import static br.com.accounting.core.util.Utils.isBlankOrNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public final class HistoricoFactory {
    private static HistoricoFactory factory;

    private Historico entity;
    private Map<String, Object> parametros;

    private HistoricoFactory() {
        entity = new Historico();
        parametros = new HashMap<>();
    }

    public static HistoricoFactory begin() {
        factory = new HistoricoFactory();
        return factory;
    }

    public Historico build() {
        return entity;
    }

    public HistoricoFactory withCodigo(String codigo) {
        if (!isBlankOrNull(codigo)) {
            entity.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public HistoricoFactory withMetodo(String metodo) {
        if (!isBlankOrNull(metodo)) {
            entity.metodo(metodo);
        }
        return this;
    }

    public HistoricoFactory withParametro(String nomeDoParametro, String valorDoParametro) {
        if (!isBlankOrNull(nomeDoParametro) && !isBlankOrNull(valorDoParametro)) {
            parametros.put(nomeDoParametro, valorDoParametro);
            withParametros(parametros);
        }
        return this;
    }

    public HistoricoFactory withParametros(Map<String, Object> parametros) {
        if (!isEmpty(parametros)) {
            entity.parametros(parametros);
        }
        return this;
    }
}
