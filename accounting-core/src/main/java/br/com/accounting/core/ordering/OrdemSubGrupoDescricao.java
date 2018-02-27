package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.SubGrupo;

import java.util.Comparator;

public class OrdemSubGrupoDescricao extends OrdemGenerica<SubGrupo> implements Ordem<SubGrupo, SubGrupo> {

    @Override
    public Comparator<SubGrupo> getComparator() {
        return Comparator.comparing(SubGrupo::getDescricao);
    }
}
