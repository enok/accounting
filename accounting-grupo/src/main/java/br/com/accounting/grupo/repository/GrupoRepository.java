package br.com.accounting.grupo.repository;

import br.com.accounting.commons.entity.Grupo;
import br.com.accounting.commons.repository.GenericRepository;

import java.util.List;

public interface GrupoRepository extends GenericRepository<Grupo> {
    Grupo filtrarPorNome(List<Grupo> entities, String nome);

    void ordenarPorNome(List<Grupo> entities);
}
