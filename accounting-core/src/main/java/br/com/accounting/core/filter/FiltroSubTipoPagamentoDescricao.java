package br.com.accounting.core.filter;

import br.com.accounting.core.entity.SubTipoPagamento;

import java.util.function.Predicate;

public class FiltroSubTipoPagamentoDescricao extends FiltroGenerico<SubTipoPagamento> implements Filtro<SubTipoPagamento, SubTipoPagamento> {
    private String descricao;

    public FiltroSubTipoPagamentoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Predicate<SubTipoPagamento> getFiltroPredicado() {
        return c -> c.getDescricao().equals(descricao);
    }
}
