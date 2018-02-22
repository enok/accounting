package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubTipoPagamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroDescricao implements CampoFiltro<SubTipoPagamento> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroDescricao.class);

    private String descricao;

    public CampoFiltroDescricao() {
    }

    public CampoFiltroDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<SubTipoPagamento> filtrar(List<SubTipoPagamento> entities) {
        LOG.info("[ filtrar ] entities: " + entities);

        return entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubTipoPagamento> ordenar(List<SubTipoPagamento> entities, Order order) {
        LOG.info("[ ordenar ] entities: " + entities + ", order: " + order);

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
