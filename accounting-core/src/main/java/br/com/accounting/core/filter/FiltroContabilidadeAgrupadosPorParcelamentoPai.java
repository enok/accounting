package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;

import java.util.function.Predicate;

public class FiltroContabilidadeAgrupadosPorParcelamentoPai extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private Long codigoPai;

    public FiltroContabilidadeAgrupadosPorParcelamentoPai(Long codigoPai) {
        this.codigoPai = codigoPai;
    }

    @Override
    public Predicate<Contabilidade> getPredicate() {
        return c -> comparacaoPai(c) || comparacaoFilhos(c);
    }

    private boolean comparacaoPai(Contabilidade c) {
        return c.getCodigo().equals(codigoPai);
    }

    private boolean comparacaoFilhos(Contabilidade c) {
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
