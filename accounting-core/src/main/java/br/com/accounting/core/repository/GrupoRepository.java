package br.com.accounting.core.repository;

import br.com.accounting.commons.repository.GenericRepository;
import br.com.accounting.core.entity.Grupo;

import java.util.List;

public interface GrupoRepository extends GenericRepository<Grupo> {
    Grupo filtrarPorNome(List<Grupo> entities, String nome);

    void ordenarPorNome(List<Grupo> entities);
}
