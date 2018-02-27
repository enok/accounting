package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;

import java.util.function.Predicate;

public class FiltroContabilidadeDescricao extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private String descricao;

    public FiltroContabilidadeDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Predicate<Contabilidade> getPredicate() {
        return c -> c.getDescricao().equals(descricao);
    }
}
