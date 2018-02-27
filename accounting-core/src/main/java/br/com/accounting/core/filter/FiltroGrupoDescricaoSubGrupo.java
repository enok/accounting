package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;

import java.util.function.Function;
import java.util.function.Predicate;

public class FiltroGrupoDescricaoSubGrupo extends FiltroMapGenerico<SubGrupo, Grupo> implements Filtro<SubGrupo, Grupo> {
    private String descricao;

    public FiltroGrupoDescricaoSubGrupo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Predicate<Grupo> getFiltroPredicado() {
        return c -> c.getDescricao().equals(descricao);
    }

    @Override
    public Function<Grupo, SubGrupo> getMapPredicado() {
        return c -> new SubGrupo(c.getSubGrupo().getCodigo(), c.getSubGrupo().getDescricao());
    }
}
