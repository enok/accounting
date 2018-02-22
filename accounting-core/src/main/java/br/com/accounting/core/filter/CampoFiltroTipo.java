package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.Tipo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroTipo implements CampoFiltro<Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroTipo.class);

    private Tipo tipo;

    public CampoFiltroTipo() {
    }

    public CampoFiltroTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ] entities: " + entities);

        return entities
                .stream()
                .filter(c -> c.getTipo().equals(tipo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contabilidade> ordenar(List<Contabilidade> entities, Order order) {
        LOG.info("[ ordenar ] entities: " + entities + ", order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .filter(c -> (c.getTipo() != null))
                        .sorted(Comparator.comparing(Contabilidade::getTipoString).reversed())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .filter(c -> (c.getTipo() != null))
                        .sorted(Comparator.comparing(Contabilidade::getTipoString))
                        .collect(Collectors.toList());
        }
    }
}
