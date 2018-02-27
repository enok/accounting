package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.TipoPagamento;

import java.util.function.Predicate;

public class FiltroContabilidadeTipoPagamento extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private TipoPagamento tipoPagamento;

    public FiltroContabilidadeTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public Predicate<Contabilidade> getPredicate() {
        return c -> c.getTipoPagamento().equals(tipoPagamento);
    }
}
