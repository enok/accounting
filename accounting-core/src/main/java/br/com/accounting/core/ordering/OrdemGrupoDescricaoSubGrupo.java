package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;

import java.util.Comparator;
import java.util.function.Function;

public class OrdemGrupoDescricaoSubGrupo extends OrdemMapaGenerica<SubGrupo, Grupo> implements Ordem<SubGrupo, Grupo> {

    @Override
    public Comparator<Grupo> getPredicate() {
        return Comparator.comparing(Grupo::getSubGrupoDescricao);
    }

    @Override
    public Function<Grupo, SubGrupo> getMap() {
        return c -> new SubGrupo(c.getSubGrupo().getCodigo(), c.getSubGrupo().getDescricao());
    }
}
