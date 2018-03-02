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
        return parcelamentoNaoNulo(c) && (igualCodigoPai(c) || igualCodigoPaiNulo(c));
    }

    private boolean parcelamentoNaoNulo(Contabilidade c) {
        return c.getParcelamento() != null;
    }

    private boolean igualCodigoPai(Contabilidade c) {
        return (c.getParcelamento().getCodigoPai() != null) && c.getParcelamento().getCodigoPai().equals(codigoPai);
    }

    private boolean igualCodigoPaiNulo(Contabilidade c) {
        return (c.getParcelamento().getCodigoPai() == null) && (codigoPai == null);
    }
}
