package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Tipo;

import java.util.function.Predicate;

public class FiltroContabilidadeTipo extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private Tipo tipo;

    public FiltroContabilidadeTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public Predicate<Contabilidade> getPredicate() {
        return c -> c.getTipo().equals(tipo);
    }
}
