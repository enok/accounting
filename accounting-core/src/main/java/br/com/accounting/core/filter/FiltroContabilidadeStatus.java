package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Status;

import java.util.function.Predicate;

public class FiltroContabilidadeStatus extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private Status status;

    public FiltroContabilidadeStatus(Status status) {
        this.status = status;
    }

    @Override
    public Predicate<Contabilidade> getPredicate() {
        return c -> c.getStatus().equals(status);
    }
}