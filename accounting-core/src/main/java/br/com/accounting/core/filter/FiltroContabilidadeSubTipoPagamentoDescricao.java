package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.SubTipoPagamento;

import java.util.function.Predicate;

public class FiltroContabilidadeSubTipoPagamentoDescricao extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private String descricao;

    public FiltroContabilidadeSubTipoPagamentoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Predicate<Contabilidade> getFiltroPredicado() {
        return c -> descricaoEhIgual(c);
    }

    private boolean descricaoEhIgual(Contabilidade c) {
        SubTipoPagamento subTipoPagamento = c.getSubTipoPagamento();
        if (subTipoPagamento == null) {
            return false;
        }
        return subTipoPagamento.getDescricao().equals(descricao);
    }
}
