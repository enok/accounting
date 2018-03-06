package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;

import java.util.function.Predicate;

public class FiltroContabilidadeCodigo extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private Long codigo;

    public FiltroContabilidadeCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Override
    public Predicate<Contabilidade> getPredicate() {
        return c -> c.getCodigo() == codigo;
    }
}
