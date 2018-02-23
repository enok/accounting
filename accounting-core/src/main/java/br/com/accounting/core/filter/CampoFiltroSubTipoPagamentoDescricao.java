package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubTipoPagamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CampoFiltroSubTipoPagamentoDescricao implements CampoFiltro<SubTipoPagamento, SubTipoPagamento> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroSubTipoPagamentoDescricao.class);

    private String descricao;

    public CampoFiltroSubTipoPagamentoDescricao() {
    }

    public CampoFiltroSubTipoPagamentoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<SubTipoPagamento> filtrar(List<SubTipoPagamento> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        Set<SubTipoPagamento> subTipoPagamentos = entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .collect(Collectors.toSet());

        return new ArrayList<>(subTipoPagamentos);
    }

    @Override
    public List<SubTipoPagamento> ordenar(List<SubTipoPagamento> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(SubTipoPagamento::getDescricao).reversed())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(SubTipoPagamento::getDescricao))
                        .collect(Collectors.toList());
        }
    }
}
