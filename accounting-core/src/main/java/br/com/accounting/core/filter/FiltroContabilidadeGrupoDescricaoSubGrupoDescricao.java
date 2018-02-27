package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;

import java.util.function.Predicate;

public class FiltroContabilidadeGrupoDescricaoSubGrupoDescricao extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private String descricaoGrupo;
    private String descricaoSubGrupo;

    public FiltroContabilidadeGrupoDescricaoSubGrupoDescricao(String descricaoGrupo, String descricaoSubGrupo) {
        this.descricaoGrupo = descricaoGrupo;
        this.descricaoSubGrupo = descricaoSubGrupo;
    }

    @Override
    public Predicate<Contabilidade> getFiltroPredicado() {
        return c -> descricaoEhIgual(c);
    }

    private boolean descricaoEhIgual(Contabilidade c) {
        return c.getGrupo().getDescricao().equals(descricaoGrupo) &&
                c.getGrupo().getSubGrupo().getDescricao().equals(descricaoSubGrupo);
    }
}
