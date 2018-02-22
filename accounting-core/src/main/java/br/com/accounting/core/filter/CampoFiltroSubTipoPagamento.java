package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubTipoPagamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroSubTipoPagamento implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroSubTipoPagamento.class);

    private String descricao;

    public CampoFiltroSubTipoPagamento() {
    }

    public CampoFiltroSubTipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> descricaoEhIgual(c))
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
                        .filter(c -> (c.getSubTipoPagamento() != null))
                        .sorted(Comparator.comparing(Contabilidade::getSubTipoPagamentoString).reversed())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .filter(c -> (c.getSubTipoPagamento() != null))
                        .sorted(Comparator.comparing(Contabilidade::getSubTipoPagamentoString))
                        .collect(Collectors.toList());
        }
    }

    private boolean descricaoEhIgual(Contabilidade c) {
        SubTipoPagamento subTipoPagamento = c.getSubTipoPagamento();
        if (subTipoPagamento == null) {
            return false;
        }
        return subTipoPagamento.getDescricao().equals(descricao);
    }
}
