package br.com.accounting.core.filter;

import br.com.accounting.core.entity.SubGrupo;

import java.util.function.Predicate;

public class FiltroSubGrupoDescricao extends FiltroGenerico<SubGrupo> implements Filtro<SubGrupo, SubGrupo> {
    private String descricao;

    public FiltroSubGrupoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Predicate<SubGrupo> getFiltroPredicado() {
        return c -> c.getDescricao().equals(descricao);
    }
}
