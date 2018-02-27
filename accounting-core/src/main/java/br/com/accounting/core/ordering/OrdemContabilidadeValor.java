package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Contabilidade;

import java.util.Comparator;

public class OrdemContabilidadeValor extends OrdemGenerica<Contabilidade> implements Ordem<Contabilidade, Contabilidade> {

    @Override
    public Comparator<Contabilidade> getComparator() {
        return Comparator.comparing(Contabilidade::getValor);
    }
}