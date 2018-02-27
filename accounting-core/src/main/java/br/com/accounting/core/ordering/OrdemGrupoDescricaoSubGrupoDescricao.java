package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Grupo;

import java.util.Comparator;

public class OrdemGrupoDescricaoSubGrupoDescricao extends OrdemDuplaGenerica<Grupo> implements Ordem<Grupo, Grupo> {

    @Override
    public Comparator<Grupo> getComparator() {
        return Comparator.comparing(Grupo::getDescricao);
    }

    @Override
    public Comparator<Grupo> getComparator2() {
        return Comparator.comparing(Grupo::getSubGrupoDescricao);
    }
}
