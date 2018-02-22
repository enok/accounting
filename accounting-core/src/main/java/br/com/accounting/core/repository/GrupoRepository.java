package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface GrupoRepository {
    Long proximoCodigo();

    void incrementarCodigo(Long proximoCodigo) throws RepositoryException;

    void salvar(Grupo subGrupo) throws RepositoryException;

    List<Grupo> buscarRegistros() throws RepositoryException;
}
