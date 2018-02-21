package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.TipoPagamento;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroTipoPagamento implements CampoFiltro<Contabilidade> {
    private TipoPagamento tipoPagamento;

    public CampoFiltroTipoPagamento() {
    }

    public CampoFiltroTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        return entities
                .stream()
                .filter(c -> c.getTipoPagamento().equals(tipoPagamento))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contabilidade> ordenar(List<Contabilidade> entities, Order order) {
        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getTipoPagamentoString).reversed())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getTipoPagamentoString))
                        .collect(Collectors.toList());
        }
    }
}
