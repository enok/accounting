package br.com.accounting.core.repository;

import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface GenericRepository<T> {
    Long proximoCodigo() throws StoreException, RepositoryException;

    void incrementarCodigo(Long codigo) throws RepositoryException;

    void salvar(T entity) throws StoreException;

    void atualizar(T entity) throws RepositoryException;

    void deletar(T entity) throws RepositoryException;

    List<T> buscarRegistros() throws StoreException, RepositoryException;

    T filtrarCodigo(List<T> entities, Long codigo);
}
