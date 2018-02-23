package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.TipoPagamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroContabilidadeTipoPagamento implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroContabilidadeTipoPagamento.class);

    private TipoPagamento tipoPagamento;

    public CampoFiltroContabilidadeTipoPagamento() {
    }

    public CampoFiltroContabilidadeTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> c.getTipoPagamento().equals(tipoPagamento))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contabilidade> ordenar(List<Contabilidade> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getTipoPagamentoValue).reversed())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getTipoPagamentoValue))
                        .collect(Collectors.toList());
        }
    }
}
