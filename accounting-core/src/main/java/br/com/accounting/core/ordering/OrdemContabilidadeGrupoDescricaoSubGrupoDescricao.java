package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Contabilidade;

import java.util.Comparator;

public class OrdemContabilidadeGrupoDescricaoSubGrupoDescricao extends OrdemDuplaGenerica<Contabilidade> implements Ordem<Contabilidade, Contabilidade> {

    @Override
    public Comparator<Contabilidade> getComparator() {
        return Comparator.comparing(Contabilidade::getGrupoDescricao);
    }

    @Override
    public Comparator<Contabilidade> getComparator2() {
        return Comparator.comparing(Contabilidade::getSubGrupoDescricao);
    }
}
