package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Grupo;

import java.util.function.Predicate;

public class FiltroGrupoDescricaoSubGrupoDescricao extends FiltroGenerico<Grupo> implements Filtro<Grupo, Grupo> {
    private String descricaoGrupo;
    private String descricaoSubGrupo;

    public FiltroGrupoDescricaoSubGrupoDescricao(String descricaoGrupo, String descricaoSubGrupo) {
        this.descricaoGrupo = descricaoGrupo;
        this.descricaoSubGrupo = descricaoSubGrupo;
    }

    @Override
    public Predicate<Grupo> getFiltroPredicado() {
        return c -> descricaoEhIgual(c);
    }

    private boolean descricaoEhIgual(Grupo c) {
        return c.getDescricao().equals(descricaoGrupo) &&
                c.getSubGrupo().getDescricao().equals(descricaoSubGrupo);
    }
}
