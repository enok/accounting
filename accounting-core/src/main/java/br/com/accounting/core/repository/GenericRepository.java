package br.com.accounting.core.repository;

import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface GenericRepository<T> {
    Long proximoCodigo() throws StoreException;

    void incrementarCodigo(Long codigo) throws StoreException, RepositoryException;

    void salvar(T entity) throws StoreException;

    void atualizar(T entity) throws StoreException;

    void deletar(T entity) throws StoreException;

    List<T> buscarRegistros() throws StoreException, RepositoryException;

    T filtrarCodigo(List<T> entities, Long codigo);
}
