package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;

import java.util.function.Predicate;

public class FiltroContabilidadeParcelamentoPai extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private Long codigoPai;

    public FiltroContabilidadeParcelamentoPai(Long codigoPai) {
        this.codigoPai = codigoPai;
    }

    @Override
    public Predicate<Contabilidade> getPredicate() {
        return c -> comparacaoCodigoPai(c);
    }

    private boolean comparacaoCodigoPai(Contabilidade c) {
        return (c.getParcelamento() != null) && (c.getParcelamento().getCodigoPai().equals(codigoPai));
    }
}
