package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;

import java.util.function.Predicate;

public class FiltroContabilidadeGrupoDescricao extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private String descricao;

    public FiltroContabilidadeGrupoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Predicate<Contabilidade> getFiltroPredicado() {
        return c -> descricaoEhIgual(c);
    }

    private boolean descricaoEhIgual(Contabilidade c) {
        return c.getGrupo().getDescricao().equals(descricao);
    }
}
