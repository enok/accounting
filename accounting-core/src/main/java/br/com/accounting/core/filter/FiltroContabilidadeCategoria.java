package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Categoria;
import br.com.accounting.core.entity.Contabilidade;

import java.util.List;
import java.util.function.Predicate;

public class FiltroContabilidadeCategoria extends FiltroGenerico<Contabilidade> implements Filtro<Contabilidade, Contabilidade> {
    private Categoria categoria;

    public FiltroContabilidadeCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public Predicate<Contabilidade> getFiltroPredicado() {
        return c -> c.getCategoria().equals(categoria);
    }
}
