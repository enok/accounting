package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Contabilidade;

import java.util.Comparator;

public class OrdemContabilidadeTipoPagamento extends OrdemGenerica<Contabilidade> implements Ordem<Contabilidade, Contabilidade> {

    @Override
    public Comparator<Contabilidade> getComparator() {
        return Comparator.comparing(Contabilidade::getTipoPagamentoValue);
    }
}
