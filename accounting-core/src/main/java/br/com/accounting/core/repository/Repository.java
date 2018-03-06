package br.com.accounting.core.repository;

import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface Repository<T> {
    Long proximoCodigo();

    void incrementarCodigo(Long proximoCodigo) throws RepositoryException;

    Long salvar(T entity) throws RepositoryException;

    void atualizar(List<T> oldEntitiesList, List<T> newEntitiesList) throws RepositoryException;

    List<T> buscarRegistros() throws RepositoryException;
}
