package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubTipoPagamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoOrdemSubTipoPagamentoDescricao implements CampoOrdem<SubTipoPagamento, SubTipoPagamento> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoOrdemSubTipoPagamentoDescricao.class);

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
