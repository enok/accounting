package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface GenericRepository<T> {
    Long proximoCodigo() throws RepositoryException;

    void incrementarCodigo(Long codigo) throws RepositoryException;

    void salvar(T entity) throws RepositoryException;

    void atualizar(T entity) throws RepositoryException;

    void deletar(T entity) throws RepositoryException;

    List<T> buscarRegistros() throws RepositoryException;
}
