package br.com.accounting.core.repository;

import br.com.accounting.commons.repository.GenericRepository;
import br.com.accounting.core.entity.SubGrupo;

import java.util.List;

public interface SubGrupoRepository extends GenericRepository<SubGrupo> {
    SubGrupo filtrarPorNome(List<SubGrupo> entities, String nome);

    void ordenarPorNome(List<SubGrupo> entities);
}
