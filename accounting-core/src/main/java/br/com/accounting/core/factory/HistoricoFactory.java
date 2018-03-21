package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Historico;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class HistoricoFactory {
    private static HistoricoFactory historicoFactory;

    private Historico historico;
    private Map<String, Object> parametros;

    private HistoricoFactory() {
        historico = new Historico();
        parametros = new HashMap<>();
    }

    public static HistoricoFactory begin() {
        historicoFactory = new HistoricoFactory();
        return historicoFactory;
    }

    public HistoricoFactory withCodigo(String codigo) {
        if (!isBlank(codigo)) {
            historico.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public HistoricoFactory withMetodo(String metodo) {
        if (!isBlank(metodo)) {
            historico.metodo(metodo);
        }
        return this;
    }

    public HistoricoFactory withParametro(String nomeDoParametro, String valorDoParametro) {
        if (!isBlank(nomeDoParametro) && !isBlank(valorDoParametro)) {
            parametros.put(nomeDoParametro, valorDoParametro);
            withParametros(parametros);
        }
        return this;
    }

    public HistoricoFactory withParametros(Map<String, Object> parametros) {
        if (!CollectionUtils.isEmpty(parametros)) {
            historico.parametros(parametros);
        }
        return this;
    }

    public Historico build() {
        return historico;
    }
}
