package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Contabilidade;

import java.util.Comparator;
import java.util.function.Predicate;

public class OrdemContabilidadeSubTipoPagamentoDescricao extends OrdemFiltroGenerica<Contabilidade> implements Ordem<Contabilidade, Contabilidade> {

    @Override
    public Predicate<Contabilidade> getPredicate() {
        return c -> (c.getSubTipoPagamento() != null);
    }

    @Override
    public Comparator<Contabilidade> getComparator() {
        return Comparator.comparing(Contabilidade::getSubTipoPagamentoDescricao);
    }
}
