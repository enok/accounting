package br.com.accounting.subgrupo.repository;

import br.com.accounting.commons.repository.GenericRepository;
import br.com.accounting.commons.entity.SubGrupo;

import java.util.List;

public interface SubGrupoRepository extends GenericRepository<SubGrupo> {
    SubGrupo filtrarPorNome(List<SubGrupo> entities, String nome);

    void ordenarPorNome(List<SubGrupo> entities);
}
