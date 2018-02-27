package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Grupo;

import java.util.function.Predicate;

public class FiltroGrupoDescricao extends FiltroGenerico<Grupo> implements Filtro<Grupo, Grupo> {
    private String descricao;

    public FiltroGrupoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Predicate<Grupo> getPredicate() {
        return c -> c.getDescricao().equals(descricao);
    }
}
