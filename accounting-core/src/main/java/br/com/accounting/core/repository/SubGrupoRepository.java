package br.com.accounting.core.repository;

import br.com.accounting.core.entity.SubGrupo;

import java.util.List;

public interface SubGrupoRepository extends GenericRepository<SubGrupo> {
    SubGrupo filtrarPorNome(final List<SubGrupo> entities, final String nome);

    void ordenarPorNome(final List<SubGrupo> entities);
}
