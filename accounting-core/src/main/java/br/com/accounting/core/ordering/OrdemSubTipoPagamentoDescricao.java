package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.SubTipoPagamento;

import java.util.Comparator;

public class OrdemSubTipoPagamentoDescricao extends OrdemGenerica<SubTipoPagamento> implements Ordem<SubTipoPagamento, SubTipoPagamento> {

    @Override
    public Comparator<SubTipoPagamento> getComparator() {
        return Comparator.comparing(SubTipoPagamento::getDescricao);
    }
}
